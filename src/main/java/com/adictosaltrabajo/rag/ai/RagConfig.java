package com.adictosaltrabajo.rag.ai;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.rag.content.retriever.neo4j.Neo4jContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.graph.neo4j.Neo4jGraph;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RagConfig {

    @Value("${neo4j.url}")
    private String neo4jUrl;

    @Value("${neo4j.username}")
    private String neo4jUsername;

    @Value("${neo4j.password}")
    private String neo4jPassword;

    @Bean
    public Driver driver() {
        return GraphDatabase.driver(neo4jUrl, AuthTokens.basic(neo4jUsername, neo4jPassword));
    }

    @Bean
    public Neo4jGraph neo4jGraph(Driver driver) {
        return Neo4jGraph.builder().driver(driver).build();
    }

    @Bean
    public Neo4jContentRetriever neo4jContentRetriever(ChatLanguageModel chatLanguageModel, Neo4jGraph neo4jGraph) {
        return Neo4jContentRetriever.builder()
                .chatLanguageModel(chatLanguageModel)
                .graph(neo4jGraph)
                .build();
    }

    @Bean
    public CrimeExpertAgent cinemaExpertAgent(ChatLanguageModel chatLanguageModel, Neo4jContentRetriever neo4jContentRetriever) {
        return AiServices.builder(CrimeExpertAgent.class)
                .chatMemory(new MessageWindowChatMemory.Builder().maxMessages(5).chatMemoryStore(new InMemoryChatMemoryStore()).build())
                .contentRetriever(neo4jContentRetriever)
                .chatLanguageModel(chatLanguageModel).build();
    }
}
