package br.com.matheusxreis.dogimages.domain.useCases

import br.com.matheusxreis.dogimages.domain.entities.ImageData
import br.com.matheusxreis.dogimages.domain.irepositories.IGetRandomImageRepository
import br.com.matheusxreis.dogimages.domain.useCases.implementations.GetRandomImageUseCase
import kotlinx.coroutines.test.runTest
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

    @Test
    fun getRandomImageUseCase_correctCase_returnsUrl() = runTest{
        //given
       val fakeRepository = object: IGetRandomImageRepository {
            override suspend fun getRandomImageRepository(): ImageData {
                return ImageData(correctUrl)
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
            override suspend fun getRandomImageRepository(): ImageData {
                return ImageData("");
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
        whenever(fakeRepository.getRandomImageRepository()).doReturn(ImageData(correctUrl))
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
            override suspend fun getRandomImageRepository(): ImageData {
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
}