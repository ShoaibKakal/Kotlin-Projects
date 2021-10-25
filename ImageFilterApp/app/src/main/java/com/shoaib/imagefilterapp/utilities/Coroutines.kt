package com.shoaib.imagefilterapp.utilities

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Coroutines {


    public fun io(work: suspend (() ->Unit)) =
        CoroutineScope(Dispatchers.IO).launch { work() }
}