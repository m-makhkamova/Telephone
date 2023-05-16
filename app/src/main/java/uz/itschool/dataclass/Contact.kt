package uz.itschool.dataclass

data class Contact(var id: Int = 0, var name:String, var surname:String, var phone:String):java.io.Serializable{
    var img:Int = 0
}