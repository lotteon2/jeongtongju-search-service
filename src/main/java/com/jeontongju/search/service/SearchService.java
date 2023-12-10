package com.jeontongju.search.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeontongju.search.client.WishCartServiceClient;
import com.jeontongju.search.document.Product;
import com.jeontongju.search.dto.PageResponseFormat;
import com.jeontongju.search.dto.request.IsWishProductDto;
import com.jeontongju.search.dto.response.*;
import com.jeontongju.search.exception.ProductNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
import org.opensearch.index.query.MultiMatchQueryBuilder;
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
      HashMap<String, Boolean> isWishInfoDto = isWishProductClient(memberId, List.of(productId));
      isLikes = isWishInfoDto.get(productId);
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

  public List<GetProductDto> getProductSellerShop(
      Long sellerId, Pageable pageable, Long consumerId) {

    SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
    BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
    boolQuery.must(new TermQueryBuilder("sellerId", sellerId));
    boolQuery.filter(new TermQueryBuilder("isActivate", true));
    boolQuery.filter(new TermQueryBuilder("isDeleted", false));
    sourceBuilder.query(boolQuery);

    sourceBuilder.size(pageable.getPageSize());
    pageable.getSort().stream()
        .forEach(
            order ->
                sourceBuilder.sort(
                    SortBuilders.fieldSort(order.getProperty())
                        .order(SortOrder.fromString(order.getDirection().name()))));

    SearchResponse searchResponse = search(sourceBuilder);

    return getProductListByIsWish(consumerId, searchResponse);
  }

  public PageResponseFormat<List<GetProductDto>> getAllProductSellerShop(
      Long sellerId, Pageable pageable, Long consumerId) {

    SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
    BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
    boolQuery.must(new TermQueryBuilder("sellerId", sellerId));
    boolQuery.filter(new TermQueryBuilder("isActivate", true));
    boolQuery.filter(new TermQueryBuilder("isDeleted", false));
    sourceBuilder.query(boolQuery);

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
    List<GetProductDto> getProductSellerShopDtoList =
        getProductListByIsWish(consumerId, searchResponse);

    return PageResponseFormat.toDto(
        hits.getTotalHits().value, pageable, getProductSellerShopDtoList);
  }

  public PageResponseFormat<List<GetProductDto>> getProductByCategory(
      Long categoryId, Pageable pageable, Long consumerId) {

    SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
    BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
    boolQuery.must(new TermQueryBuilder("categoryId", categoryId));
    boolQuery.filter(new TermQueryBuilder("isActivate", true));
    boolQuery.filter(new TermQueryBuilder("isDeleted", false));

    sourceBuilder.query(boolQuery);
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
    List<GetProductDto> getProductDtoList = getProductListByIsWish(consumerId, searchResponse);

    return PageResponseFormat.toDto(hits.getTotalHits().value, pageable, getProductDtoList);
  }

  public PageResponseFormat<List<GetProductDto>> getProductBySearch(
      String query, Pageable pageable, Long consumerId) {

    SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
    BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

    MultiMatchQueryBuilder multiMatchQuery =
        QueryBuilders.multiMatchQuery(query, "name", "description", "rawMaterial.text").field("name", 2);

    boolQuery.must(multiMatchQuery);

    boolQuery.filter(new TermQueryBuilder("isActivate", true));
    boolQuery.filter(new TermQueryBuilder("isDeleted", false));

    sourceBuilder.query(boolQuery);

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
    List<GetProductDto> getProductDtoList = getProductListByIsWish(consumerId, searchResponse);

    return PageResponseFormat.toDto(hits.getTotalHits().value, pageable, getProductDtoList);
  }

  /** 상품 목록 조회 일때, 찜 유무 */
  public List<GetProductDto> getProductListByIsWish(
      Long consumerId, SearchResponse searchResponse) {

    if (consumerId != null) {
      List<Product> productList = new ArrayList<>();
      List<String> productIds = new ArrayList<>();

      Arrays.stream(searchResponse.getHits().getHits())
          .forEach(
              hit -> {
                productList.add(objectMapper.convertValue(hit.getSourceAsMap(), Product.class));
                productIds.add(hit.getId());
              });

      HashMap<String, Boolean> isWishInfoDto = isWishProductClient(consumerId, productIds);

      return productList.stream()
          .map(product -> GetProductDto.toDto(product, isWishInfoDto.get(product.getProductId())))
          .collect(Collectors.toList());

    } else {
      return Arrays.stream(searchResponse.getHits().getHits())
          .map(
              hit ->
                  GetProductDto.toDto(
                      objectMapper.convertValue(hit.getSourceAsMap(), Product.class), false))
          .collect(Collectors.toList());
    }
  }

  /** isWish feign client */
  public HashMap<String, Boolean> isWishProductClient(Long consumerId, List<String> productIds) {
    return wishCartServiceClient
        .getIsWish(IsWishProductDto.builder().consumerId(consumerId).productIds(productIds).build())
        .getData();
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
