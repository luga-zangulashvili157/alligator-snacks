// luka gogokhia davaleba 1 bankis aplikacia

// sabaziso klasi (enkapsulacia da memkvidreobis safuzvlebi) VVVV

open class Account(
    val accNum: String, //es read-only aris
    val owneri: String // mflobeli
    
) {
    
    private var balansi: Double = 0.0 // pirobis mixedvitt, private 

    
    fun balansisnaxva(): Double { // so FUN!!!1
        
        return balansi // getter funqcia balansistvis
        
    }

    
    fun deposit(amounti: Double) { // depozitis funqcia
        if (amounti > 0) {
            balansi += amounti
            println("$$amounti -is depoziti shesrulda. axali balansi: $$balansi")
        } 
        
        else {
            println("0-ze meti unda chawero depoziti")
        }
        
        
    }


    open fun gamotana(amounti: Double) { // gamotanis funqcia. override rom gavuketot, sheizleba
        
        if (amounti > 0 && balansi >= amounti) {
            balansi -= amounti
            println("$$amounti -is gamotana moxerxda. axali balansi: $$balansi")
            
        }
        else {
            println("gamotana ver moxerxda. araa sakmarisi tanxa an araa sworad chawerili")
            
            
        }
    }

    
    fun printInfo() { // accountis informacias gamoitans
        println("accountis nomeri: $accNum")
        println("mflobeli: $owneri")
        
        println("balansi: $$balansi")
        println("gilocavt axal wels")
    }
}

// axla meore nawili,memkvidreoba

// svingsaccount klasit daviwyot

class SavingsAccount(accNum: String, owneri: String) : Account(accNum, owneri) {
    
    override fun gamotana(amounti: Double) {
        if (amounti > 500) {
            println("gamotanis limits gadacdit. maqsimaluri sheizleba 1 gamotanashi 500$")
            
        } 
        else {
            //parentis gamotanis funqcia gamoviyenot
            super.gamotana(amounti)
        }
        
    }
}


// VIPaccountis klasi
class VIPAccount(
    
    accNum: String,
    owneri: String,
    val transactionFee: Double = 2.0 // default gadasaxadi
    
    
) : Account(accNum, owneri) {
    
    override fun gamotana(amounti: Double) {
        
        val mtliani = amounti + transactionFee
        
        if (balansisnaxva() >= mtliani && amounti > 0) {
            // balanss chven ver shevcvlit pirdapir, radgan private aris hoda parentis gamotanis funqcias viyenebt rom magan shecvalos. gadasaxads chven totalAmount-shi vdebt
            
            super.gamotana(mtliani)
            println("vip $$amounti -is  gamotana (+$transactionFee gadasaxadit) warmatebit chatarda")
        } 
        
        else {
            println("vip tanxis gamotana ver moxerxda. araa sakmarisi tanxa, an arasworadaa chawerili tanxa")
        }
        
    }
}



// axla mesame nawili, testireba main funqciis
fun main() {
    val jameschicken = SavingsAccount("S067", "james chicken")
    
    val ploinkius = VIPAccount("V420", "zauri kinkilauri")

    // depozitebi
    jameschicken.deposit(1000.0)
    ploinkius.deposit(1000.0)

    
    //gamovitanot tanxa
    jameschicken.gamotana(600.0) // ar unda moxerxdes radgan 500 dolars gadaawarba
    jameschicken.gamotana(400.0) // unda gamoitanos

    ploinkius.gamotana(500.0)     // unda gamoitanos 502 dolari jamshi imitom rom gadasaxadia 2 dolari

    // accountis saboloo informacia gamovitanot
    println("\n accountis saboloo informacia")
    jameschicken.printInfo()
    ploinkius.printInfo()
}

// laik and subskraib for more kode!!!!!!!!!!!!!