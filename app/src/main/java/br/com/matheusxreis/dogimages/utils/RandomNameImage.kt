package br.com.matheusxreis.dogimages.utils

class RandomNameImage {

    companion object {
        fun generate(url:String):String{
            val number = (1..250).random()
            val part1 = url.reversed()
                .replace("http://wwww.", "")
                .replace(".", "")
                .replace("api", "")
                .replace("/", "")
                .replace(":", "")
            val part2 = "puppy-$number"
            val part3 = "dogimages${(1..14).random()}"

            val final = "$part1$part2$part3"

            if(number>125){
                return final.reversed()
            }
            return final
        }
    }
}