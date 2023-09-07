package com.babyblackdog.ddogdog.place.hotel.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.babyblackdog.ddogdog.place.PlaceTestData;
import com.babyblackdog.ddogdog.place.hotel.model.Hotel;
import com.babyblackdog.ddogdog.place.hotel.repository.HotelRepository;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Transactional
class HotelRestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private HotelRepository repository;
  @Autowired
  private PlaceTestData placeTestData;

  private Hotel savedHotel;

  @BeforeEach
  void setUp() {
    savedHotel = repository.save(placeTestData.getHotel());
  }

  @Test
  @DisplayName("지역 이름으로 숙소를 조회한다.")
  void getPlacesByProvince_ReadSuccess() throws Exception {
    // Given
    String provinceName = savedHotel.getAddressValue();

    // When & Then
    mockMvc.perform(get("/hotels")
            .param("province", provinceName)
            .param("page", "0")
            .param("size", "5")
            .accept(APPLICATION_JSON_VALUE)
            .contentType(APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.hotelResponses.content", Matchers.hasSize(1)))
        .andDo(print())
        .andDo(document("hotel-get-by-province",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            queryParameters(
                parameterWithName("province").description("지역 이름"),
                parameterWithName("page").description("페이지"),
                parameterWithName("size").description("크기")
            ),
            responseFields(
                fieldWithPath("hotelResponses").type(OBJECT).description("숙소 응답"),
                fieldWithPath("hotelResponses.content").type(ARRAY).description("숙소 정보 배열"),
                fieldWithPath("hotelResponses.content[].id").type(NUMBER).description("숙소 아이디"),
                fieldWithPath("hotelResponses.content[].name").type(STRING).description("숙소 이름"),
                fieldWithPath("hotelResponses.content[].address").type(STRING)
                    .description("숙소 지역 이름"),
                fieldWithPath("hotelResponses.content[].adminId").type(NUMBER)
                    .description("관리자 아이디"),
                fieldWithPath("hotelResponses.content[].contact").type(STRING)
                    .description("숙소 문의번호"),
                fieldWithPath("hotelResponses.content[].representative").type(STRING)
                    .description("숙소 대표자명"),
                fieldWithPath("hotelResponses.content[].businessName").type(STRING)
                    .description("숙소 사업장명"),

                fieldWithPath("hotelResponses.pageable").type(OBJECT)
                    .description("pageable").ignored(),
                fieldWithPath("hotelResponses.pageable.pageNumber").type(NUMBER)
                    .description("pageNumber"),
                fieldWithPath("hotelResponses.pageable.pageSize").type(NUMBER)
                    .description("pageSize"),
                fieldWithPath("hotelResponses.pageable.sort").type(OBJECT)
                    .description("pageSize"),
                fieldWithPath("hotelResponses.pageable.sort.empty").type(BOOLEAN)
                    .description("pageSize"),
                fieldWithPath("hotelResponses.pageable.sort.unsorted").type(BOOLEAN)
                    .description("pageSize"),
                fieldWithPath("hotelResponses.pageable.sort.sorted").type(BOOLEAN)
                    .description("pageSize"),
                fieldWithPath("hotelResponses.pageable.offset").type(NUMBER)
                    .description("offset"),
                fieldWithPath("hotelResponses.pageable.paged").type(BOOLEAN)
                    .description("paged"),
                fieldWithPath("hotelResponses.pageable.unpaged").type(BOOLEAN)
                    .description("unpaged"),
                fieldWithPath("hotelResponses.last").type(BOOLEAN).description("last")
                    .ignored(),
                fieldWithPath("hotelResponses.totalPages").type(JsonFieldType.NUMBER)
                    .description("totalPages"),
                fieldWithPath("hotelResponses.totalElements").type(JsonFieldType.NUMBER)
                    .description("totalElements"),
                fieldWithPath("hotelResponses.first").type(BOOLEAN)
                    .description("first").ignored(),
                fieldWithPath("hotelResponses.size").type(JsonFieldType.NUMBER).description("size")
                    .ignored(),
                fieldWithPath("hotelResponses.number").type(JsonFieldType.NUMBER)
                    .description("number").ignored(),
                fieldWithPath("hotelResponses.sort").type(OBJECT)
                    .description("sort"),
                fieldWithPath("hotelResponses.sort.empty").type(BOOLEAN)
                    .description("sort.empty").ignored(),
                fieldWithPath("hotelResponses.sort.unsorted").type(BOOLEAN)
                    .description("sort.unsorted").ignored(),
                fieldWithPath("hotelResponses.sort.sorted").type(BOOLEAN)
                    .description("sort.sorted").ignored(),
                fieldWithPath("hotelResponses.numberOfElements").type(JsonFieldType.NUMBER)
                    .description("numberOfElements").ignored(),
                fieldWithPath("hotelResponses.empty").type(BOOLEAN)
                    .description("empty").ignored()
            )
        ));
  }

  @Test
  @DisplayName("숙소 아이디로 숙소를 조회한다.")
  void getPlace_ReadSuccess() throws Exception {
    // Given
    Long placeId = savedHotel.getId();

    // When & Then
    mockMvc.perform(get("/hotels/{hotelId}", placeId)
            .accept(APPLICATION_JSON_VALUE)
            .contentType(APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.name").value(savedHotel.getName()))
        .andDo(print())
        .andDo(document("hotel-get",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            pathParameters(
                parameterWithName("hotelId").description("숙소 아이디")
            ),
            responseFields(
                fieldWithPath("id").type(NUMBER).description("숙소 아이디"),
                fieldWithPath("name").type(STRING).description("숙소 이름"),
                fieldWithPath("address").type(STRING).description("숙소 지역 이름"),
                fieldWithPath("adminId").type(NUMBER).description("관리자 아이디"),
                fieldWithPath("contact").type(STRING).description("숙소 문의번호"),
                fieldWithPath("representative").type(STRING).description("숙소 대표자명"),
                fieldWithPath("businessName").type(STRING).description("숙소 사업장명")
            )
        ));
  }

}