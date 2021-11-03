package com.dis.logolink.editor
import org.junit.Test
import org.junit.Assert.*

class EditorClassesTest {
    private fun eingangsListenGenerator(boolList: List<Boolean>): MutableList<Input> {
        val inputList = mutableListOf<Input>()
        val itr = boolList.iterator()
        while (itr.hasNext()) {
            inputList.add(Input(itr.next()))
        }
        return inputList
    }

    //List an Generierten Klassen ist wie folge eingeteilt
//List[0](NotGate)  (True->False,False->True)
//List[1](AndGate)  ((True,True),(True,False),(False,False))
//List[2](NndGate)  ((True,True),(True,False),(False,False))
//List[3](OrGate)   ((True,True),(True,False),(False,False))
//List[4](NorGate)  ((True,True),(True,False),(False,False))
//List[5](XorGate)  ((True,True),(True,False),(False,False))
//List[6](XnorGate) ((True,True),(True,False),(False,False))
    private fun generateTestSetOfComponents(): MutableList<MutableList<Component>> {
        val defaultPosition = Position(0, 0)
        val trueInput = Input(true)
        val falseInput = Input(false)
        val inputListMixed = mutableListOf(trueInput, falseInput)
        val inputListTrue = mutableListOf(trueInput, trueInput)
        val inputListFalse = mutableListOf(falseInput, falseInput)
        val nameMixed = "Component Mixed"
        val nameTrue = "Component True"
        val nameFalse = "Component False"
        return mutableListOf(
            mutableListOf(
                NotGate(defaultPosition, inputListTrue, nameTrue),
                NotGate(defaultPosition, inputListFalse, nameFalse)
            ),
            mutableListOf(
                AndGate(defaultPosition, inputListTrue, nameTrue),
                AndGate(defaultPosition, inputListMixed, nameMixed),
                AndGate(defaultPosition, inputListFalse, nameFalse)
            ),
            mutableListOf(
                NandGate(defaultPosition, inputListTrue, nameTrue),
                NandGate(defaultPosition, inputListMixed, nameMixed),
                NandGate(defaultPosition, inputListFalse, nameFalse)
            ),
            mutableListOf(
                OrGate(defaultPosition, inputListTrue, nameTrue),
                OrGate(defaultPosition, inputListMixed, nameMixed),
                OrGate(defaultPosition, inputListFalse, nameFalse)
            ),
            mutableListOf(
                NorGate(defaultPosition, inputListTrue, nameTrue),
                NorGate(defaultPosition, inputListMixed, nameMixed),
                NorGate(defaultPosition, inputListFalse, nameFalse)
            ),
            mutableListOf(
                XorGate(defaultPosition, inputListTrue, nameTrue),
                XorGate(defaultPosition, inputListMixed, nameMixed),
                XorGate(defaultPosition, inputListFalse, nameFalse)
            ),
            mutableListOf(
                XnorGate(defaultPosition, inputListTrue, nameTrue),
                XnorGate(defaultPosition, inputListMixed, nameMixed),
                XnorGate(defaultPosition, inputListFalse, nameFalse)
            ),
        )
    }

    @Test
    fun eingangsListenGeneratorTest() {
        val inputList = listOf(true, false, true)
        val actualInput = eingangsListenGenerator(inputList)
        assertTrue(actualInput[0].value)
        assertFalse(actualInput[1].value)
        assertTrue(actualInput[2].value)
    }

    //AndGate Tests
    @Test
    fun andGateAllTrueTest() {
        val position = Position(2, 3)
        val inputList: MutableList<Input> = eingangsListenGenerator(listOf(true, true, true))
        val andGate = AndGate(position, inputList, "Und-Gatter 1")
        val actual = andGate.result
        assertEquals(true, actual)
    }

    @Test
    fun andGateNotAllTrueTest() {
        val position = Position(21, 45)
        val inputList: MutableList<Input> =
            eingangsListenGenerator(listOf(true, false, true, true, false))
        val andGate = AndGate(position, inputList, "Und-Gatter 2")
        val actual = andGate.result
        assertEquals(false, actual)
    }

    @Test
    fun andGateAllFalseTest() {
        val position = Position(2, 3)
        val inputList: MutableList<Input> = eingangsListenGenerator(listOf(false, false))
        val andGate = AndGate(position, inputList, "Und-Gatter 1")
        val actual = andGate.result
        assertEquals(false, actual)
    }

    @Test
    fun plusOperatorAndGateTest() {
        val inputTrue = Input(true)
        val inputFalse = Input(false)
        val inputList0: MutableList<Input> = eingangsListenGenerator((listOf(true, true)))
        val inputList1: MutableList<Input> = eingangsListenGenerator((listOf(true, false)))
        val inputList2: MutableList<Input> = eingangsListenGenerator((listOf(false, false)))
        val andGate1 = AndGate(Position(0, 0), inputList0, "andGateTrueTrue")
        val andGate2 = AndGate(Position(0, 1), inputList1, "andGateTrueFalse")
        val andGate3 = AndGate(Position(1, 0), inputList2, "andGateFalseFalse")
        val actualGate10 = inputTrue + andGate1
        val actualGate20 = inputTrue + andGate2
        val actualGate30 = inputTrue + andGate3
        val actualGate11 = andGate1 + inputFalse
        val actualGate21 = andGate2 + inputFalse
        val actualGate31 = andGate3 + inputFalse
        val actualgate40 = andGate1 + andGate3
        val actualgate41 = andGate3 + andGate1
        val actualgate42 = andGate2 + andGate1
        val actualgate43 = andGate1 + andGate1
        val expectedName = "AndGate through addition with Input"
        val expectedResultFalse = false
        //True+(True)
        assertEquals(2, actualGate10.inputList.size)
        assertTrue(actualGate10.result)
        assertEquals(actualGate10.name, expectedName)
        //True+(False1)
        assertEquals(2, actualGate20.inputList.size)
        assertEquals(actualGate20.name, expectedName)
        assertEquals(actualGate20.result, expectedResultFalse)
        //True+(False2)
        assertEquals(2, actualGate30.inputList.size)
        assertEquals(actualGate30.name, expectedName)
        assertEquals(actualGate30.result, expectedResultFalse)
        //False+(True)
        assertEquals(2, actualGate11.inputList.size)
        assertEquals(actualGate11.name, expectedName)
        assertEquals(actualGate11.result, expectedResultFalse)
        //False+(False1)
        assertEquals(2, actualGate21.inputList.size)
        assertEquals(actualGate21.name, expectedName)
        assertEquals(actualGate21.result, expectedResultFalse)
        //False+(false2)
        assertEquals(2, actualGate31.inputList.size)
        assertEquals(actualGate31.name, expectedName)
        assertEquals(actualGate31.result, expectedResultFalse)

        //Gates
        //True + False ->false
        assertFalse(actualgate40.result)
        assertEquals(actualgate40.inputList, mutableListOf(andGate1.output, andGate3.output))
        //False + True -> false
        assertFalse(actualgate41.result)
        assertEquals(actualgate41.inputList, mutableListOf(andGate3.output, andGate1.output))
        //False + True -> false
        assertFalse(actualgate42.result)
        assertEquals(actualgate42.inputList, mutableListOf(andGate2.output, andGate1.output))
        //True + True -> True
        assertTrue(actualgate43.result)
        assertEquals(actualgate43.inputList, mutableListOf(andGate1.output, andGate1.output))
    }

    @Test
    fun timesOperatorAndGateTest() {
        val inputTrue = Input(true)
        val inputFalse = Input(false)
        val inputList0: MutableList<Input> = eingangsListenGenerator((listOf(true, true)))
        val inputList1: MutableList<Input> = eingangsListenGenerator((listOf(true, false)))
        val inputList2: MutableList<Input> = eingangsListenGenerator((listOf(false, false)))
        val andGate1 = AndGate(Position(0, 0), inputList0, "andGateTrueTrue")
        val andGate2 = AndGate(Position(0, 1), inputList1, "andGateTrueFalse")
        val andGate3 = AndGate(Position(1, 0), inputList2, "andGateFalseFalse")

        val actualGate10 = inputTrue * andGate1
        val actualGate20 = inputTrue * andGate2
        val actualGate30 = inputTrue * andGate3
        val actualGate11 = andGate1 * inputFalse
        val actualGate21 = andGate2 * inputFalse
        val actualGate31 = andGate3 * inputFalse
        val actualgate40 = andGate1 * andGate3
        val actualgate41 = andGate3 * andGate1
        val actualgate42 = andGate2 * andGate1
        val actualgate43 = andGate3 * andGate3

        assertTrue(actualGate10.result)
        assertTrue(actualGate20.result)
        assertTrue(actualGate30.result)
        assertTrue(actualGate11.result)
        assertFalse(actualGate21.result)
        assertFalse(actualGate31.result)
        assertTrue(actualgate40.result)
        assertEquals(actualgate40.inputList, mutableListOf(andGate1.output, andGate3.output))
        assertTrue(actualgate41.result)
        assertEquals(actualgate41.inputList, mutableListOf(andGate3.output, andGate1.output))
        assertTrue(actualgate42.result)
        assertEquals(actualgate42.inputList, mutableListOf(andGate2.output, andGate1.output))
        assertFalse(actualgate43.result)
        assertEquals(actualgate43.inputList, mutableListOf(andGate3.output, andGate3.output))

    }

    //OrGate Tests
    @Test
    fun orGateAllTrueTest() {
        val position = Position(2, 3)
        val inputList: MutableList<Input> = eingangsListenGenerator(listOf(true, true))
        val orGate = OrGate(position, inputList, "Or-Gatter 1")
        val actual = orGate.result
        assertEquals(true, actual)
    }

    @Test
    fun orGateNotAllTrueTest() {
        val position = Position(2, 3)
        val inputList: MutableList<Input> = eingangsListenGenerator(listOf(true, false, false))
        val orGate = OrGate(position, inputList, "Or-Gatter 2")
        val actual = orGate.result
        assertEquals(true, actual)
    }

    @Test
    fun orGateAllFalseTest() {
        val position = Position(2, 3)
        val inputList: MutableList<Input> = eingangsListenGenerator(listOf(false, false))
        val orGate = OrGate(position, inputList, "Or-Gatter 3")
        val actual = orGate.result
        assertEquals(false, actual)
    }

    @Test
    fun plusOperatorOrGateTest() {
        val inputTrue = Input(true)
        val inputFalse = Input(false)
        val inputList0: MutableList<Input> = eingangsListenGenerator((listOf(true, true)))
        val inputList1: MutableList<Input> = eingangsListenGenerator((listOf(true, false)))
        val inputList2: MutableList<Input> = eingangsListenGenerator((listOf(false, false)))
        val orGate10 = OrGate(Position(0, 0), inputList0, "orGateTrueTrue")
        val orGate20 = OrGate(Position(0, 1), inputList1, "orGateTrueFalse")
        val orGate30 = OrGate(Position(1, 0), inputList2, "orGateFalseFalse")
        val actualGate10 = inputTrue + orGate10
        val actualGate20 = inputTrue + orGate20
        val actualGate30 = inputTrue + orGate30
        val actualGate11 = orGate10 + inputFalse
        val actualGate21 = orGate20 + inputFalse
        val actualGate31 = orGate30 + inputFalse
        val actualgate40 = orGate10 + orGate30
        val actualgate41 = orGate30 + orGate10
        val actualgate42 = orGate20 + orGate10
        val actualgate43 = orGate10 + orGate10
        val expectedName = "AndGate through addition with Input"
        //True+(True)
        assertEquals(2, actualGate10.inputList.size)
        assertTrue(actualGate10.result)
        assertEquals(actualGate10.name, expectedName)
        //True+(False1)
        assertEquals(2, actualGate20.inputList.size)
        assertEquals(actualGate20.name, expectedName)
        assertTrue(actualGate20.result)
        //True+(False2)
        assertEquals(2, actualGate30.inputList.size)
        assertEquals(actualGate30.name, expectedName)
        assertFalse(actualGate30.result)
        //False+(True)
        assertEquals(2, actualGate11.inputList.size)
        assertEquals(actualGate11.name, expectedName)
        assertFalse(actualGate11.result)
        //False+(False1)
        assertEquals(2, actualGate21.inputList.size)
        assertEquals(actualGate21.name, expectedName)
        assertFalse(actualGate21.result)
        //False+(false2)
        assertEquals(2, actualGate31.inputList.size)
        assertEquals(actualGate31.name, expectedName)
        //Gate+Gate
        assertFalse(actualGate31.result)
        assertFalse(actualgate40.result)
        assertEquals(actualgate40.inputList, mutableListOf(orGate10.output, orGate30.output))
        assertFalse(actualgate41.result)
        assertEquals(actualgate41.inputList, mutableListOf(orGate30.output, orGate10.output))
        assertTrue(actualgate42.result)
        assertEquals(actualgate42.inputList, mutableListOf(orGate20.output, orGate10.output))
        assertTrue(actualgate43.result)
        assertEquals(actualgate43.inputList, mutableListOf(orGate10.output, orGate10.output))
    }

    @Test
    fun timesOperatorOrGateTest() {
        val inputTrue = Input(true)
        val inputFalse = Input(false)
        val inputList0: MutableList<Input> = eingangsListenGenerator((listOf(true, true)))
        val inputList1: MutableList<Input> = eingangsListenGenerator((listOf(true, false)))
        val inputList2: MutableList<Input> = eingangsListenGenerator((listOf(false, false)))
        val orGate1 = OrGate(Position(0, 0), inputList0, "orGateTrueTrue")
        val orGate2 = OrGate(Position(0, 1), inputList1, "orGateTrueFalse")
        val orGate3 = OrGate(Position(1, 0), inputList2, "orGateFalseFalse")

        val actualGate10 = inputTrue * orGate1
        val actualGate20 = inputTrue * orGate2
        val actualGate30 = inputTrue * orGate3
        val actualGate11 = orGate1 * inputFalse
        val actualGate21 = orGate2 * inputFalse
        val actualGate31 = orGate3 * inputFalse
        val actualgate40 = orGate1 * orGate3
        val actualgate41 = orGate3 * orGate1
        val actualgate42 = orGate2 * orGate1
        val actualgate43 = orGate3 * orGate3

        assertTrue(actualGate10.result)
        assertTrue(actualGate20.result)
        assertTrue(actualGate30.result)
        assertTrue(actualGate11.result)
        assertTrue(actualGate21.result)
        assertFalse(actualGate31.result)
        assertFalse(actualGate31.result)
        assertTrue(actualgate40.result)
        assertEquals(actualgate40.inputList, mutableListOf(orGate1.output, orGate3.output))
        assertTrue(actualgate41.result)
        assertEquals(actualgate41.inputList, mutableListOf(orGate3.output, orGate1.output))
        assertTrue(actualgate42.result)
        assertEquals(actualgate42.inputList, mutableListOf(orGate2.output, orGate1.output))
        assertFalse(actualgate43.result)
        assertEquals(actualgate43.inputList, mutableListOf(orGate3.output, orGate3.output))
    }


    //NotGate
    @Test
    fun notGateFalseTest() {
        val position = Position(2, 3)
        val inputList: MutableList<Input> = eingangsListenGenerator(listOf(false))
        val notGate = NotGate(position, inputList, "Not-Gatter 1")
        val actual = notGate.result
        assertEquals(true, actual)
    }

    @Test
    fun notGateTrueTest() {
        val position = Position(2, 3)
        val inputList: MutableList<Input> = eingangsListenGenerator(listOf(true))
        val notGate = NotGate(position, inputList, "Not-Gatter 2")
        val actual = notGate.result
        assertEquals(false, actual)
    }
//List an Generierten Klassen ist wie folge eingeteilt
//List[0](NotGate)  (True->False,False->True)
//List[1](AndGate)  ((True,True),(True,False),(False,False))
//List[2](NandGate)  ((True,True),(True,False),(False,False))
//List[3](OrGate)   ((True,True),(True,False),(False,False))
//List[4](NorGate)  ((True,True),(True,False),(False,False))
//List[5](XorGate)  ((True,True),(True,False),(False,False))
//List[6](XnorGate) ((True,True),(True,False),(False,False))

    @Test
    fun componentArithmeticTest() {
        val componentsList = generateTestSetOfComponents()
        val inputTrue = Input(true)
        val inputFalse = Input(false)
        //gibt es meine ich keine Anwednung für val NotGate = componentsList[0][0] + componentsList[1][0]
        //Input + Input
        val andGateInputTrue = inputTrue + inputTrue
        val andGateInputFalse = inputTrue + inputFalse
        //Input + Gate
        val andGateIGTrue = inputTrue + componentsList[1][0]
        val andGateIGFalse = inputFalse + componentsList[1][0]
        //Gate + Input
        val andGateGITrue = componentsList[1][0] + inputTrue
        val andGateGIFalse = componentsList[1][0] + inputFalse
        //Gate + Gate
        val andGateGGTrue = componentsList[1][0] + componentsList[2][2]
        //Input*Input
        val orGateMixedTrue = inputTrue * inputFalse
        val orGateFalseFalse = inputFalse * inputFalse
        //Input * Gate
        val orGateIGTrue = inputTrue * componentsList[6][1]
        val orGateIGFalse = inputFalse * componentsList[4][0]
        //Gate * Input
        val orGateGITrue = componentsList[6][1] * inputTrue
        val orGateGIFalse = componentsList[4][0] * inputFalse
        //Gate*Gate
        val orGateGGFalse = componentsList[5][0] * componentsList[2][0]
        //!Input
        val inputFalseThroughTrue = !inputTrue
        //!Gate
        val andGateThroughNand = !componentsList[2][0]
        val nandGateThroughAnd = !componentsList[1][2]
        val orGateThroughNor = !componentsList[4][0]
        val norGateThroughOr = !componentsList[3][2]
        val xorGateThroughXnor = !componentsList[6][1]
        val xnorGateThroughXor = !componentsList[5][1]

        //fancy shit
        //  Gate = !(!(XorGate(False,False) * XnorGate(True,True) + !NandGate(TrueFalse))
        //  Gate = !(!OrGate(false,true) + AndGate(TrueFalse))
        //  Gate = !(NorGate(False,True) + AndGate(True,False))
        //  Gate = !(AndGate(False,False)
        //  Gate = NandGate(False,False)
        val gate = !(!(componentsList[5][2] * componentsList[6][0]) + !componentsList[2][1])

        //asserts Abfrage nach Ergebnis und InputListe
        assertTrue(andGateInputTrue.result)
        assertEquals(mutableListOf(inputTrue, inputTrue), andGateInputTrue.inputList)
        assertFalse(andGateInputFalse.result)
        assertEquals(mutableListOf(inputTrue, inputFalse), andGateInputFalse.inputList)
        assertTrue(andGateIGTrue.result)
        assertEquals(mutableListOf(inputTrue, componentsList[1][0].output), andGateIGTrue.inputList)
        assertFalse(andGateIGFalse.result)
        assertEquals(
            mutableListOf(inputFalse, componentsList[1][0].output),
            andGateIGFalse.inputList
        )
        assertTrue(andGateGITrue.result)
        assertEquals(mutableListOf(componentsList[1][0].output, inputTrue), andGateGITrue.inputList)
        assertFalse(andGateGIFalse.result)
        assertEquals(
            mutableListOf(componentsList[1][0].output, inputFalse),
            andGateGIFalse.inputList
        )
        assertTrue(andGateGGTrue.result)
        assertEquals(
            mutableListOf(componentsList[1][0].output, componentsList[2][2].output),
            andGateGGTrue.inputList
        )
        assertTrue(orGateMixedTrue.result)
        assertEquals(mutableListOf(inputTrue, inputFalse), orGateMixedTrue.inputList)
        assertFalse(orGateFalseFalse.result)
        assertEquals(mutableListOf(inputFalse, inputFalse), orGateFalseFalse.inputList)
        assertTrue(orGateIGTrue.result)
        assertEquals(mutableListOf(inputTrue, componentsList[6][1].output), orGateIGTrue.inputList)
        assertFalse(orGateIGFalse.result)
        assertEquals(
            mutableListOf(inputFalse, componentsList[4][0].output),
            orGateIGFalse.inputList
        )
        assertTrue(orGateGITrue.result)
        assertEquals(mutableListOf(componentsList[6][1].output, inputTrue), orGateGITrue.inputList)
        assertFalse(orGateGIFalse.result)
        assertEquals(
            mutableListOf(componentsList[4][0].output, inputFalse),
            orGateGIFalse.inputList
        )
        assertFalse(orGateGGFalse.result)
        assertEquals(
            mutableListOf(componentsList[5][0].output, componentsList[2][0].output),
            orGateGGFalse.inputList
        )
        assertFalse(inputFalseThroughTrue.value)
        assertTrue(andGateThroughNand.result)
        assertEquals(componentsList[2][0].inputList, andGateThroughNand.inputList)
        assertTrue(nandGateThroughAnd.result)
        assertEquals(componentsList[1][2].inputList, nandGateThroughAnd.inputList)
        assertTrue(orGateThroughNor.result)
        assertEquals(componentsList[4][0].inputList, orGateThroughNor.inputList)
        assertTrue(norGateThroughOr.result)
        assertEquals(componentsList[3][2].inputList, norGateThroughOr.inputList)
        assertTrue(xorGateThroughXnor.result)
        assertEquals(componentsList[6][1].inputList, xorGateThroughXnor.inputList)
        assertFalse(xnorGateThroughXor.result)
        assertEquals(componentsList[5][1].inputList, xnorGateThroughXor.inputList)

        assertTrue(gate.result)
        assertFalse(gate.inputList[0].value)
        assertFalse(gate.inputList[1].value)
    }
}