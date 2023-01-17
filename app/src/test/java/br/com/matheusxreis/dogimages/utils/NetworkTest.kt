package br.com.matheusxreis.dogimages.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import org.junit.Assert.*
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.junit.Test

class NetworkTest {
    @Test
    fun network_isConnectedAnyWay_returnsTrue(){
        // given
        val mockNetworkCapabilities = mock<NetworkCapabilities> {
            on { hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) } doReturn true
            on { hasTransport(NetworkCapabilities.TRANSPORT_WIFI)}  doReturn true
            on { hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)} doReturn true
        }
        val mockConnectivityManager = mock<ConnectivityManager> {
            on { getNetworkCapabilities(it.activeNetwork) } doReturn mockNetworkCapabilities
        }
        val mockContext = mock<Context> {
            on { getSystemService(Context.CONNECTIVITY_SERVICE) } doReturn mockConnectivityManager
        }
        val sut = Network
       // when
        val result = sut.isConnected(mockContext)
        // then
        assertTrue(result)
   }
    @Test
    fun network_isNotConnectAnyWay_returnsFalse(){
        // given
        val mockNetworkCapabilities = mock<NetworkCapabilities> {
            on { hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) } doReturn false
            on { hasTransport(NetworkCapabilities.TRANSPORT_WIFI)}  doReturn false
            on { hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)} doReturn false
        }
        val mockConnectivityManager = mock<ConnectivityManager> {
            on { getNetworkCapabilities(it.activeNetwork) } doReturn mockNetworkCapabilities
        }
        val mockContext = mock<Context> {
            on { getSystemService(Context.CONNECTIVITY_SERVICE) } doReturn mockConnectivityManager
        }
        val sut = Network
        // when
        val result = sut.isConnected(mockContext)
        // then
       assertFalse(result)
    }
}