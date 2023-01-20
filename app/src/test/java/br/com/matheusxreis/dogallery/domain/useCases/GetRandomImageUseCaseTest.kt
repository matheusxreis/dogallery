package br.com.matheusxreis.dogallery.domain.useCases

import br.com.matheusxreis.dogallery.domain.entities.ImageData
import br.com.matheusxreis.dogallery.domain.errors.BadRequest
import br.com.matheusxreis.dogallery.domain.errors.NotFound
import br.com.matheusxreis.dogallery.domain.irepositories.IGetRandomImageRepository
import br.com.matheusxreis.dogallery.domain.useCases.implementations.GetRandomImageUseCase
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

class GetRandomImageUseCaseTest {
    //given -> my class

    //when -> it execute the method

    //then -> the result should be this

    private val correctUrl = "https://fakeUrl.com.br"
    private val correctResponse = Response.success(200, ImageData(correctUrl))
    private val emptyStringResponse = Response.success(200, ImageData(""))
    private val badRequestResponse = Response.error<ImageData>(400,"".toResponseBody())
    private val notFoundRequestResponse = Response.error<ImageData>(404, "".toResponseBody())


    @Test
    fun getRandomImageUseCase_correctCase_returnsUrl() = runTest{
        //given
       val fakeRepository = object: IGetRandomImageRepository {
            override suspend fun getRandomImageRepository(): Response<ImageData> {
                return correctResponse
            }
        }
        val sut = GetRandomImageUseCase(fakeRepository)
        //when
        val result = sut.execute()
        // then
        assertEquals(result, correctUrl)
    }

    @Test
    fun getRandomImageUseCase_emptyUrl_returnsError() {
        // given
        val fakeRepository = object: IGetRandomImageRepository {
            override suspend fun getRandomImageRepository(): Response<ImageData> {
                return emptyStringResponse
            }
        }
        val sut = GetRandomImageUseCase(fakeRepository)
        //when and then
        val result = assertThrows(
            Exception::class.java)  {
            runTest {
                sut.execute()
            }
        }

    }

     @Test
    fun getRandomImageUseCase_repository_calledOneTime() = runTest {
       //given
        val fakeRepository: IGetRandomImageRepository = mock()
        whenever(fakeRepository.getRandomImageRepository()).doReturn(correctResponse)
        val sut = GetRandomImageUseCase(fakeRepository)
        //when
        sut.execute()
        //then
        verify(fakeRepository, times(1)).getRandomImageRepository()
    }

    @Test
    fun getRandomImageUseCase_repositoryThrows_returnsThrows() {
        // given
        val fakeRepository = object: IGetRandomImageRepository {
            override suspend fun getRandomImageRepository(): Response<ImageData> {
                throw Exception()
            }
        }
        val sut = GetRandomImageUseCase(fakeRepository)
        //when and then
        assertThrows(Exception::class.java){
            runTest {
                sut.execute()
            }
        }
    }

    @Test
    fun getRandomImageUseCase_repositoryError400_returnThrowsBadRequest(){

        // given
        val fakeRepository = object: IGetRandomImageRepository {
            override suspend fun getRandomImageRepository(): Response<ImageData> {
                return badRequestResponse
            }
        }
        val sut = GetRandomImageUseCase(fakeRepository)
        //when and then
        assertThrows(BadRequest::class.java){
            runTest {
                sut.execute()
            }
        }
    }

    @Test
    fun getRandomImageUseCase_repositoryError404_returnThrowsNotFound(){
        // given
        val fakeRepository = object: IGetRandomImageRepository {
            override suspend fun getRandomImageRepository(): Response<ImageData> {
                return notFoundRequestResponse
            }
        }
        val sut = GetRandomImageUseCase(fakeRepository)
        //when and then
        assertThrows(NotFound::class.java){
            runTest {
                sut.execute()
            }
        }
    }
}