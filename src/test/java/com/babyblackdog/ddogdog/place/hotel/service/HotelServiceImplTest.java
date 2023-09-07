package com.babyblackdog.ddogdog.place.hotel.service;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.HOTEL_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.babyblackdog.ddogdog.global.exception.HotelException;
import com.babyblackdog.ddogdog.place.PlaceTestData;
import com.babyblackdog.ddogdog.place.hotel.model.Hotel;
import com.babyblackdog.ddogdog.place.hotel.model.vo.Province;
import com.babyblackdog.ddogdog.place.hotel.repository.HotelRepository;
import com.babyblackdog.ddogdog.place.hotel.service.dto.AddHotelParam;
import com.babyblackdog.ddogdog.place.hotel.service.dto.HotelResult;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
@Transactional
class HotelServiceImplTest {

  @Autowired
  private HotelService hotelService;

  @Autowired
  private HotelRepository hotelRepository;
  @Autowired
  private PlaceTestData placeTestData;

  private Hotel hotel;

  @BeforeEach
  void setUp() {
    this.hotel = placeTestData.getHotelEntity();
  }

  @Test
  @DisplayName("유효한 숙소 추가 요청으로 추가한다.")
  void registerHotel_CreateSuccess() {
    // Given
    AddHotelParam param = placeTestData.getAddHotelParam();

    // When
    HotelResult hotelResult = hotelService.registerHotel(param);

    // Then
    assertThat(hotelResult.address()).isEqualTo(param.province().getValue());
    assertThat(hotelResult.name()).isEqualTo(param.hotelName().getValue());
  }

  @Test
  @DisplayName("유효한 지역 이름으로 숙소를 조회하면 성공한다.")
  void findHotelsInProvince_ReadSuccess() {
    // Given
    Hotel savedHotel = hotelRepository.save(hotel);

    // When
    Page<HotelResult> result = hotelService.findHotelsInProvince(
        savedHotel.getAddress(),
        PageRequest.of(0, 2));

    // Then
    assertThat(result)
        .isNotNull()
        .isNotEmpty();
    assertThat(result.getContent()).hasSize(1);
  }

  @Test
  @DisplayName("유효하지 않은 지역 이름으로 숙소를 조회하면 실패한다.")
  void findHotelsInProvince_ReadException() {
    // Given
    Province invalidProvince = new Province("평양");

    // When
    Page<HotelResult> result = hotelService.findHotelsInProvince(
        invalidProvince,
        PageRequest.of(0, 2));

    // Then
    assertThat(result)
        .isNotNull()
        .isEmpty();
  }

  @Test
  @DisplayName("유효한 숙소 아이디를 이용해 숙소를 조회하면 성공한다.")
  void findPlaceById_ReadSuccess() {
    // Given
    Hotel savedHotel = hotelRepository.save(hotel);

    // When
    HotelResult result = hotelService.findHotelById(savedHotel.getId());

    // Then
    assertThat(result).isEqualTo(HotelResult.of(savedHotel));
  }

  @Test
  @DisplayName("유효하지 않은 숙소 아이디를 이용해 숙소를 조회하면 실패한다.")
  void findPlaceById_ReadException() {
    // Given
    Long invalidId = 1L;

    // When & Then
    assertThatThrownBy(() -> hotelService.findHotelById(invalidId))
        .isInstanceOf(HotelException.class)
        .hasMessage(HOTEL_NOT_FOUND.toString());
  }

}