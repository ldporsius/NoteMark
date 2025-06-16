package nl.codingwithlinda.notemark.tests.exercises

interface Juicer<in T: JuicyPlant>{
    fun makeJuice(from: T)
}

interface JuicyPlant{
    fun method()
}
interface JuicyFruit: JuicyPlant
interface JuicyVegetable: JuicyPlant

class Orange: JuicyFruit{
    override fun method() {
        println("peel and process")
    }
}
enum class Fruit: JuicyFruit{
    APPLE{
        override fun method() {
            println("cut and process")
        }
    },
    ORANGE{
        override fun method() {
            println("peel and process")
        }
    }
}

enum class Vegetable: JuicyVegetable{
    CARROT{
        override fun method() {
            println("grate and squeeze")
        }
    }
}
class OrangeJuicer: Juicer<Orange>{
    override fun makeJuice(from: Orange) {
        from.method()
    }
}

class FruitJuicer: Juicer<JuicyFruit>{
    val ingredients = listOf(Fruit.APPLE, Orange())
    init{
        for (ingredient in ingredients) {
            makeJuice(ingredient)
        }
    }

    override fun makeJuice(from: JuicyFruit) {
        from.method()
    }
}

class VegetableJuicer: Juicer<JuicyVegetable>{
    val ingredients = listOf(Vegetable.CARROT)
    init {
        for(ing in ingredients){
            makeJuice(ing)
        }
    }
    override fun makeJuice(from: JuicyVegetable) {
        from.method()
    }
}

class MultiVitamineJuicer: Juicer<JuicyPlant>{
    val ingredients: List<JuicyPlant> = listOf(Orange(), Vegetable.CARROT)
    init {
        for(ing in ingredients){
            makeJuice(ing)
        }
    }
    override fun makeJuice(from: JuicyPlant) {
        from.method()
    }
}
