package com.dis.logolink.editor


//formel AndGate(1,0) / OrGate(1,1)
//formel !(XorGate(0,0) + !NorGate(1,0)) / !NandGate(0,1)
class GameEditor(formula: String) {
    lateinit var formulaList : MutableList<Component>
    init {
        var formulaElements = formula.split(" ")
        var itr = formulaElements.iterator()
       // var
        while(itr.hasNext()){
            var notMarker = itr.next().contains("!")
            when {
                itr.next().contains("OrGate") -> {}
                itr.next().contains("AndGate") -> {
                //    componentList.add(createComponent(itr.next()))
                }
                itr.next().contains("OrGate") -> {}
                itr.next().contains("NotGate") -> {}
                itr.next().contains("XorGate") -> {}
            }
        }
    }
}