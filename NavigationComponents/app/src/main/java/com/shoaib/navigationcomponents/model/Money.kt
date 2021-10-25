package com.shoaib.navigationcomponents.model
import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


import java.math.BigDecimal

@Parcelize
 class Money(val amount: BigDecimal) : Parcelable