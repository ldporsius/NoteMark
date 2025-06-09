package nl.codingwithlinda.notemark.tests.exercises


interface Money
object Quarter: Money
object Dime: Money

interface VendorProduct
object Gum: VendorProduct {
    fun chewing() = println("Chewing on gum")
}

object Chips: VendorProduct {
    fun crunching() = println("Crunching on chips")
}

class VendingMachine{
    fun sell(money: Money): VendorProduct {
        when (money) {
            is Quarter -> {
                println("You entered a quarter")
                return Gum
            }
            is Dime -> {
                println("You entered a dime")
                return Chips
            }
            else -> {
                throw RuntimeException("Unknown money")
            }
        }
    }
}

class ChildrenAtLunch{
    private val vendingMachine = VendingMachine()
    fun buySomething(money: Money) {
        val product = vendingMachine.sell(money)
        if (product is Gum) {
            product.chewing()
        } else if (product is Chips) {
            product.crunching()
        }
    }

    fun <T: VendorProduct>castTypeBuySomething(money: Money): T{
        val product = vendingMachine.sell(money)
        return product as T
    }
    init {
        val chips = castTypeBuySomething<Chips>(Dime)
        chips.crunching()

        val gum = castTypeBuySomething<Gum>(Quarter)
        gum.chewing()

        val definitelyChips = typeSafeBuySomethingInline<Chips>(vendingMachine, Dime)
        definitelyChips.crunching()
    }
}

inline fun<reified T: VendorProduct> typeSafeBuySomethingInline(
    vendingMachine: VendingMachine,
    money: Money,
): T{
    val product = vendingMachine.sell(money) as T
    return product
}
