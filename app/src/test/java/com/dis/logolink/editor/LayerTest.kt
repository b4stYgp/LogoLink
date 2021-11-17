package com.dis.logolink.editor
import org.junit.Test
import org.junit.Assert.*
class LayerTest {

    @Test
    fun layerGenerator() {
        /**
         * checks references.
         * If at any point pass by value is used, Input inversion should not change the result.
         * Resulting in a failed assertion.
         */

        /**
         * default: (i00, i01) = (false, false) => i20 = false
         *
         * -i00- |OR00| -i10-
         * -i01-
         *
         * -i00- |NOT00| -i11-
         *
         * -i10- |AND10| -i20-
         * -i11-
         *
         * inversion: (i00, !i01) = (false, true) => i20 = true
         */

        val tmpPosition = Position(0,0)

        val defaultInput0 = IdentityGate(tmpPosition, mutableListOf<Component>(), "Identity0")
        val defaultInput1 =  IdentityGate(tmpPosition, mutableListOf<Component>(), "Identity1")

        val defaultInputList = mutableListOf<Component>(defaultInput0, defaultInput1)

        val mappingList = mutableListOf(
            mutableListOf(
                mutableListOf(0,1),
                mutableListOf(0)
            ),
            mutableListOf(
                mutableListOf(0,1)
            )
        )
        val componentList = mutableListOf(
            mutableListOf("OR", "NOT"),
            mutableListOf("AND")
        )
        val level0 = Level(defaultInputList, mappingList, componentList)


        assert(!level0.layerList[1].componentList[0].result)
        defaultInput1.invert()
        assert(level0.layerList[1].componentList[0].result)
    }
}