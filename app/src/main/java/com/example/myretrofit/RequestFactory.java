package com.example.myretrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestFactory {
    public Builder mBuilder;

    private RequestFactory(Builder builder){
        mBuilder = builder;
    }

    public static RequestFactory parseAnnotations(Method method) {
        return new Builder(method).build();
    }

    public static class Builder {

        final Method method;
        String httpMethod;
        public String baseUrl;
        String relativeUrl;
        final Annotation[] methodAnnotations;
        Set<String> relativeUrlParamNames;
        final Annotation[][] parameterAnnotationsArray;
        final Type[] parameterTypes;
        ParameterHandler[] parameterHandlers;

        private static final String PARAM = "[a-zA-Z][a-zA-Z0-9_-]*";
        private static final Pattern PARAM_URL_REGEX = Pattern.compile("\\{(" + PARAM + ")\\}");


        Builder(Method method) {
            this.method = method;
            this.methodAnnotations = method.getAnnotations();
            this.parameterTypes = method.getGenericParameterTypes();
            this.parameterAnnotationsArray = method.getParameterAnnotations();
        }

        RequestFactory build(){
            for (Annotation annotation : methodAnnotations) {
                parseMethodAnnotation(annotation);
            }

            int parameterCount = parameterAnnotationsArray.length;
            parameterHandlers = new ParameterHandler[parameterCount];
            for (int p = 0; p < parameterCount; p++) {
                parameterHandlers[p] =
                        parseParameter(parameterAnnotationsArray[p]);
            }
            return new RequestFactory(Builder.this);
        }

        private void parseMethodAnnotation(Annotation annotation) {
            if (annotation instanceof GET) {
                parseHttpMethodAndPath("GET", ((GET) annotation).value(), false);
            }
        }

        private void parseHttpMethodAndPath(String httpMethod, String value, boolean hasBody) {
            //防止一个请求中出现多个Method
            if (this.httpMethod != null) {
                throw new IllegalArgumentException( "Only one HTTP method is allowed.");
            }

            this.httpMethod = httpMethod;

            //防止相对路径为空
            if (value.isEmpty()) {
                return;
            }

            //TODO：获取相对路径中的查询量

            this.relativeUrl = value;
            this.relativeUrlParamNames = parsePathParameters(value);
        }

        //解析路径中的待定参数
        static Set<String> parsePathParameters(String path) {
            Matcher m = PARAM_URL_REGEX.matcher(path);
            Set<String> patterns = new LinkedHashSet<>();
            while (m.find()) {
                patterns.add(m.group(1));
            }
            return patterns;
        }

        private ParameterHandler parseParameter(Annotation[] annotations){
            if(annotations != null){
                for (Annotation annotation : annotations) {
                    ParameterHandler annotationAction =
                            parseParameterAnnotation(annotation);
                    return annotationAction;
                }
            }
            return null;
        }

        private ParameterHandler parseParameterAnnotation(Annotation annotation) {

            //这里解析@Path的参数注解
            if (annotation instanceof Path) {
                Path path = (Path) annotation;
                String name = path.value();
                return new ParameterHandler(name);
            }
            else {
                return null;
            }
        }
    }
}
