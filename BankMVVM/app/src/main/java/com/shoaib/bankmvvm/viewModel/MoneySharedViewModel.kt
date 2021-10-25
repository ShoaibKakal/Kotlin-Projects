package com.shoaib.bankmvvm.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MoneySharedViewModel : ViewModel() {

    /*
        attributes {name, balance}
        functions {deposit(), withdraw(), transfer(), checkBalance()}
     */

    private val _customerName = MutableLiveData("")
    val customerName: LiveData<String> = _customerName           //backing Property

    private val _currentBalance = MutableLiveData(0.0)
    val currentBalance: LiveData<Double> = _currentBalance      //backing property

//    private val _amount = MutableLiveData(0.0)
//    val amount: LiveData<Double> = _amount

    init {
        _customerName.value = ""
        _currentBalance.value = 15.0

    }



    fun setCurrentBalance(amount: Double)
    {
        _currentBalance.value = amount
    }



    fun deposit(amount: Double)
    {
        _currentBalance.value = _currentBalance.value?.plus(amount)
    }



    fun withdraw(amount: Double)
    {
        _currentBalance.value = _currentBalance.value?.minus(amount)

    }



    fun transfer(amount: Double, receiverName: String)
    {

        _currentBalance.value?.minus(amount)
    }

    fun setCustomerName(name: String)
    {
        _customerName.value = name
    }

}