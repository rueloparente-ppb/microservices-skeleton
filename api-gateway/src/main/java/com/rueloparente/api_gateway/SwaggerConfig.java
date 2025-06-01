package com.rueloparente.api_gateway;

class SwaggerConfig {
    // private final RouteDefinitionLocator locator;
    // private final SwaggerUiConfigProperties swaggerUiConfigProperties;
    //
    // public SwaggerConfig(RouteDefinitionLocator locator, SwaggerUiConfigProperties swaggerUiConfigProperties) {
    // 	this.locator = locator;
    // 	this.swaggerUiConfigProperties = swaggerUiConfigProperties;
    // }
    //
    // @PostConstruct
    // public void init() {
    // 	List<RouteDefinition> definitions =
    // 			locator.getRouteDefinitions().collectList().block();
    // 	Set<AbstractSwaggerUiConfigProperties.SwaggerUrl> urls = new HashSet<>();
    // 	definitions.stream()
    // 						 .filter(routeDefinition -> routeDefinition.getId().matches(".*-service"))
    // 						 .forEach(routeDefinition -> {
    // 							 String name = routeDefinition.getId().replaceAll("-service", "");
    // 							 AbstractSwaggerUiConfigProperties.SwaggerUrl swaggerUrl =
    // 									 new AbstractSwaggerUiConfigProperties.SwaggerUrl(
    // 											 name, DEFAULT_API_DOCS_URL + "/" + name, null);
    // 							 urls.add(swaggerUrl);
    // 						 });
    // 	swaggerUiConfigProperties.setUrls(urls);
    // }
}
