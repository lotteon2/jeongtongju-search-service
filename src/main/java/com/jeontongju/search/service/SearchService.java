package com.jeontongju.search.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeontongju.search.client.WishCartServiceClient;
import com.jeontongju.search.document.Product;
import com.jeontongju.search.dto.PageResponseFormat;
import com.jeontongju.search.dto.request.IsWishProductDto;
import com.jeontongju.search.dto.response.GetMyProductDto;
import com.jeontongju.search.dto.response.GetSellerOneProductDto;
import com.jeontongju.search.dto.response.IsWishInfoDto;
import com.jeontongju.search.dto.response.ProductDetailsDto;
import com.jeontongju.search.exception.ProductNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opensearch.action.get.GetRequest;
import org.opensearch.action.get.GetResponse;
import org.opensearch.action.search.SearchRequest;
import org.opensearch.action.search.SearchResponse;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.index.query.BoolQueryBuilder;
import org.opensearch.index.query.QueryBuilders;
import org.opensearch.index.query.TermQueryBuilder;
import org.opensearch.search.SearchHits;
import org.opensearch.search.builder.SearchSourceBuilder;
import org.opensearch.search.sort.SortBuilders;
import org.opensearch.search.sort.SortOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

  private static final String PRODUCT_INDEX = "product";
  private final RestHighLevelClient client;
  private final ObjectMapper objectMapper;
  private final WishCartServiceClient wishCartServiceClient;

  public ProductDetailsDto getProductDetails(String productId, Long memberId) {

    BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
    boolQuery.must(new TermQueryBuilder("productId", productId));
    boolQuery.filter(new TermQueryBuilder("isActivate", true));
    boolQuery.filter(new TermQueryBuilder("isDeleted", false));

    SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
    sourceBuilder.query(boolQuery);

    SearchResponse searchResponse = search(sourceBuilder);

    Boolean isLikes = false;

    Product product =
        Arrays.stream(searchResponse.getHits().getHits())
            .map(hit -> objectMapper.convertValue(hit.getSourceAsMap(), Product.class))
            .findFirst()
            .orElseThrow(ProductNotFoundException::new);

    if (memberId != null) {
      List<IsWishInfoDto> isWishInfoDto =
          wishCartServiceClient
              .getIsWish(IsWishProductDto.builder().consumerId(memberId).build())
              .getData();
      isLikes = isWishInfoDto.get(0).getIsLikes();
    }

    return ProductDetailsDto.toDto(product, isLikes);
  }

  public PageResponseFormat<List<GetMyProductDto>> getMyProduct(Long sellerId, Pageable pageable) {

    SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
    sourceBuilder.query(QueryBuilders.termQuery("sellerId", sellerId));
    sourceBuilder.from(pageable.getPageNumber() * pageable.getPageSize() + 1);
    sourceBuilder.size(pageable.getPageSize());

    pageable.getSort().stream()
        .forEach(
            order ->
                sourceBuilder.sort(
                    SortBuilders.fieldSort(order.getProperty())
                        .order(SortOrder.fromString(order.getDirection().name()))));

    SearchResponse searchResponse = search(sourceBuilder);
    SearchHits hits = searchResponse.getHits();

    List<GetMyProductDto> getMyProductDtoList =
        Arrays.stream(hits.getHits())
            .map(
                hit ->
                    GetMyProductDto.toDto(
                        objectMapper.convertValue(hit.getSourceAsMap(), Product.class)))
            .collect(Collectors.toList());

    return PageResponseFormat.toDto(hits.getTotalHits().value, pageable, getMyProductDtoList);
  }

  public PageResponseFormat<List<GetSellerOneProductDto>> getSellerOneProduct(
      Long sellerId, Pageable pageable) {

    SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
    sourceBuilder.query(QueryBuilders.termQuery("sellerId", sellerId));
    sourceBuilder.from(pageable.getPageNumber() * pageable.getPageSize() + 1);
    sourceBuilder.size(pageable.getPageSize());

    pageable.getSort().stream()
        .forEach(
            order ->
                sourceBuilder.sort(
                    SortBuilders.fieldSort(order.getProperty())
                        .order(SortOrder.fromString(order.getDirection().name()))));

    SearchResponse searchResponse = search(sourceBuilder);
    SearchHits hits = searchResponse.getHits();

    List<GetSellerOneProductDto> getSellerOneProductList =
        Arrays.stream(hits.getHits())
            .map(
                hit ->
                    GetSellerOneProductDto.toDto(
                        objectMapper.convertValue(hit.getSourceAsMap(), Product.class)))
            .collect(Collectors.toList());

    return PageResponseFormat.toDto(hits.getTotalHits().value, pageable, getSellerOneProductList);
  }

  /** document get */
  public GetResponse getDocument(GetRequest request) {

    try {
      GetResponse response = client.get(request, RequestOptions.DEFAULT);

      if (!response.isExists()) {
        throw new ProductNotFoundException();
      }
      return response;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /** document search */
  public SearchResponse search(SearchSourceBuilder searchSourceBuilder) {

    try {
      SearchRequest searchRequest = new SearchRequest(PRODUCT_INDEX);
      searchRequest.source(searchSourceBuilder);

      return client.search(searchRequest, RequestOptions.DEFAULT);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
