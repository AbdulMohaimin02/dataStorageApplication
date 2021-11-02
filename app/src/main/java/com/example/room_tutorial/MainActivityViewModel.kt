package com.example.room_tutorial

import android.app.Application
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.room_tutorial.data.User
import com.example.room_tutorial.data.UserDatabase
import com.example.room_tutorial.data.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<User>>
    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase((application)).userDao()
        repository = UserRepository(userDao)
        readAllData = repository.readAllData
    }
    
    // Mutable state for Showing the dialog 
    private var _showDialog = MutableLiveData(false)
    val showDialog: LiveData<Boolean> = _showDialog

    // Mutable state for First name entry field
    private val _firstName = MutableLiveData("")
    val firstName: LiveData<String> = _firstName

    // Mutable state for Last Name entry field
    private val _lastName = MutableLiveData("")
    val lastName: LiveData<String> = _lastName


    // Mutable state for Age entry field
    private val _age = MutableLiveData("")
    val age: LiveData<String> = _age

    // Variable for storing a reference to the current item
    private var _currentItem = MutableLiveData(0)
    val currentItem : LiveData<Int> = _currentItem

    
    
    



    // function for changing the first name
    fun onFirstChange(it:String){
        _firstName.value = it

    }

    // function for changing the last name
    fun onLastChange(it:String){
        _lastName.value = it
    }

    // function for changing the age name
    fun onAgeChange(it:String){
        _age.value = it
    }

    // Function for changing the reference to the current item
    fun onCurrentItemChange(id:Int){
        _currentItem.value = id
    }

    // Function to change the show dialog variable, for the alert dialog
    fun onShowDialogChange(inValue: Boolean){
        _showDialog.value = inValue

    }

    // Function to add data from text fields into database
     fun insertToDatabase(): Boolean{
        val localFirstName = firstName.value
        val localLastName = lastName.value
        val localAge = age.value

        if (checkData(firstName = localFirstName, lastName =  localLastName, age = localAge)){
            // Create a User instance
            // Even though Room will make the primary key, we still need to specify a 0
            val user = User(
                id = 0,
                firstName = localFirstName.toString(),
                lastName = localLastName.toString(),
                age = localAge.toString()
            )

            addUser(user)

            return true
        } else {
            return false
        }


    }




    // Initial function called to update the user in the database
    fun updateValues(): Boolean{
        val localFirstName = firstName.value
        val localLastName = lastName.value
        val localAge = age.value
        val currentItemId = currentItem.value.toString().toInt()

        if (checkData(firstName = localFirstName, lastName =  localLastName, age = localAge)){
            // Method for updating the user
            val updatedUser = User(currentItemId,localFirstName.toString(),localLastName.toString(),localAge.toString())

            updateUser(user = updatedUser)

            return true
        } else {
            return false
        }


    }

    // Function to change to update the value of current item in the database
    private fun updateUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(user)
        }
    }

    // Initial function called to delete the user in the database
    fun deleteThisUser(): Boolean{
        val localFirstName = firstName.value
        val localLastName = lastName.value
        val localAge = age.value
        val currentItemId = currentItem.value.toString().toInt()


        // Method for updating the user
        val userToDelete = User(currentItemId,localFirstName.toString(),localLastName.toString(),localAge.toString())

        deleteUser(userToDelete)

        return true

    }

    // Function called to delete the User in the database
    private fun deleteUser(user:User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(user)
        }

    }



    // Function to delete all users from the database

    fun deleteAllUser(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAll()
        }
    }



    // Function to check if all the fields are filled out
    private fun checkData(firstName:String?, lastName:String?, age:String?): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && TextUtils.isEmpty(age))
    }





    // Always run database operations on  a background thread
    private fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO){
            repository.addUser(user)
        }
    }
}