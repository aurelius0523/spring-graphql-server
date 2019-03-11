package com.aurelius.graph.configuration;

import com.aurelius.graph.resolver.GraphQLDataFetchers;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;

@Configuration
public class GraphConfiguration {
    private GraphQL graphQL;

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    @Autowired
    GraphQLDataFetchers graphQLDataFetchers;

    @PostConstruct
    public void init() throws IOException {
        URL url = Resources.getResource("schema.graphqls");
        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                            .type(TypeRuntimeWiring.newTypeWiring("Query")
                                                   .dataFetcher("bookById", graphQLDataFetchers.getBookByIdDataFetcher())
                                                   .dataFetcher("books", graphQLDataFetchers.getBookDataFetcher()))

                            .type(TypeRuntimeWiring.newTypeWiring("Book")
                                                   .dataFetcher("pageCount", graphQLDataFetchers.getPageCountDataFetcher())
                                                   .dataFetcher("author", graphQLDataFetchers.getAuthorDataFetcher()))


                            .build();
    }
}
