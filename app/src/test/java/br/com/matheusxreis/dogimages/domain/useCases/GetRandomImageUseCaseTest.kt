package br.com.matheusxreis.dogimages.domain.useCases

import br.com.matheusxreis.dogimages.domain.entities.ImageData
import br.com.matheusxreis.dogimages.domain.irepositories.IGetRandomImageRepository
import br.com.matheusxreis.dogimages.domain.useCases.implementations.GetRandomImageUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

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


}