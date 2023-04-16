package com.bda.userservice.service;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.FieldAndFormat;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.KnnSearchRequest;
import co.elastic.clients.elasticsearch.core.KnnSearchResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.knn_search.KnnSearchQuery;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.ml.GetTrainedModelsStatsResponse;
import co.elastic.clients.elasticsearch.ml.InferTrainedModelRequest;
import co.elastic.clients.elasticsearch.ml.InferTrainedModelResponse;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.json.JsonpMapper;
import com.bda.userservice.ElasticConfig;
import jakarta.json.stream.JsonGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ElasticService {

    private final String INDEX = "image-search-index";
    private final String INFER_MODEL_IM_SEARCH = "sentence-transformers__clip-vit-b-32-multilingual-v1";
    private final String[] sourceFields = {"name", "unit", "badge", "type", "year", "about", "origin", "gs_bucket", "gs_public_url"};

    private final Logger logger = LoggerFactory.getLogger("service");
    private final ElasticsearchClient client;

    public ElasticService() {
        client = ElasticConfig.getClientInstance();
    }

    private List<FieldAndFormat> getFields() {
        return Arrays.stream(sourceFields)
                .map(field -> FieldAndFormat.of(f -> f.field(field)))
                .collect(Collectors.toList());
    }

    public String generalSearch(String searchQuery) throws IOException {
        Query query = new Query.Builder()
                .combinedFields(
                        f -> f.query(searchQuery)
                                .fields(Arrays.asList("name^2", "about", "tags"))
                ).build();
        SearchRequest request = SearchRequest.of(
                q -> q.index(INDEX).query(query).fields(getFields())
                        .source(
                                s -> {
                                    s.fetch(true);
                                    return s;
                                }
                        ).sort(
                                sort -> sort.field(v -> v.field("_score")
                                        .order(SortOrder.Desc))
                        )
        );
        SearchResponse<Object> response = client.search(request, Object.class);
        return response.hits().hits().toString();
    }

    public String searchRandom() throws IOException {
        String dateSeed = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Query query = new Query.Builder().functionScore(
                fs -> fs.functions(rf -> rf.randomScore(fv -> fv.seed(dateSeed)))
        ).build();
        SearchRequest request = SearchRequest.of(
                q -> q.index(INDEX).query(query).fields(getFields())
                        .source(
                                s -> {
                                    s.fetch(true);
                                    return s;
                                }
                        ).size(1)
        );
        SearchResponse<Void> response = client.search(request, Void.class);
        return response.hits().hits().toString();
    }

    private List<Float> inferTrainedModel(String query) throws IOException {
        InferTrainedModelRequest request = InferTrainedModelRequest.of(
                r -> r.modelId(INFER_MODEL_IM_SEARCH).docs(Map.of("text_field", JsonData.of(query)))
        );
        InferTrainedModelResponse response = client.ml().inferTrainedModel(request);
        return response.inferenceResults().get(0).predictedValue().stream().map(fieldValue -> (float) fieldValue.doubleValue()).collect(Collectors.toList());
    }

    public String knnSearch(String queryText) throws IOException {
        List<Float> embedding = inferTrainedModel(queryText);
        KnnSearchQuery query = new KnnSearchQuery.Builder()
                .queryVector(embedding)
                .k(10)
                .numCandidates(10000)
                .field("image_embedding")
                .build();
        KnnSearchRequest request = KnnSearchRequest.of(
                r -> r.index(INDEX).knn(query).fields(Arrays.asList(sourceFields)).source(s -> s.fetch(true))
        );
        KnnSearchResponse<Void> response = client.knnSearch(request, Void.class);
        return response.hits().hits().toString();
    }

    public String getMemesByIds(List<String> ids) throws IOException {
        Query query = new Query.Builder().ids(
                idQuery -> idQuery.values(ids)
        ).build();
        SearchRequest request = SearchRequest.of(
                q -> q.index(INDEX).query(query).fields(getFields())
                        .source(
                                s -> {
                                    s.fetch(true);
                                    return s;
                                }
                        )
        );
        SearchResponse<Void> response = client.search(request, Void.class);
        return response.hits().hits().toString();
    }

    public boolean isModelUpAndRunning() throws IOException {
        GetTrainedModelsStatsResponse response = client.ml().getTrainedModelsStats(request -> request.modelId(INFER_MODEL_IM_SEARCH));
        return response.trainedModelStats().get(0).deploymentStats() != null;
    }

    public String getJsonString(List<Hit<Void>> hits) {
        JsonpMapper mapper = client._jsonpMapper();
        StringWriter writer = new StringWriter();
        try (JsonGenerator generator = mapper.jsonProvider().createGenerator(writer)) {
            mapper.serialize(hits, generator);
            hits.stream().forEach(
                    hit -> mapper.serialize(hits, generator)
            );
        }
        return writer.toString();
    }
}
