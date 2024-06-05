package com.dicoding.tanaminai.data.dummy

import com.dicoding.tanaminai.R

object DataDummy {
    val plants = listOf(
        PlantModel(
            "apple",
            R.drawable.def,
            "apple adalah blblblbla",
            "beli di supermarket"
        ),
        PlantModel(
            "chickpea",
            R.drawable.def,
            "apple adalah konsol",
            "beli aja repot amat, kaga usah nanem nanem"
        ),
        PlantModel(
            "grapes",
            R.drawable.grapes,
            "A grape is a fruit, botanically a berry, of the deciduous woody vines of the flowering plant genus Vitis. Grapes are a non-climacteric type of fruit, generally occurring in clusters. The cultivation of grapes began perhaps 8,000 years ago, and the fruit has been used as human food over history. \n" +
                    "Grapes are a type of fruit that grow in clusters of 15 to 300, and can be crimson, black, dark blue, yellow, green, orange, and pink. \"White\" grapes are actually green in color, and are evolutionarily derived from the purple grape.",
            "To grow grapes successfully, you must know which variety to select based on your region and how you want to use them. Grapes of different types are hardy in Zones 4-10, so there's a grape for your location. Use this guide to get all the details, including tips on planting, growing conditions, harvesting, and pruning.\n" +
                    "\n" +
                    "Where to Plant Grapes\n" +
                    "Grapes tolerate a variety of soil types. Well-draining soil provides the biggest harvest. Highly fertile soil is not essential; grapes grow in dry sandy soil as well as fertile black loam. Choose a site that receives at least six hours of bright sunlight a day. Beware of nearby trees or buildings that might cast shade.\n" +
                    "\n" +
                    "Herbicide drift is another consideration, especially in rural areas. Broadleaf herbicides, such as 2,4-D and dicamba, injure grape vines. Choose a site protected from herbicide drift by large trees and inform your neighbors of your grape planting. In urban areas, encourage neighbors to apply broadleaf herbicides in the fall when the herbicide is most effective and does the least damage to grape vines.\n" +
                    "How and When to Plant Grapes\n" +
                    "Spring is the best time to plant grapes, especially in cold zones. This gives them the most time to get established before winter sets in. Many mail-order nurseries sell them as bare roots. Before you plant, cut the existing roots back to 6 inches; this will encourage feeder roots to grow near the trunk. Soak bare-root plants in a bucket of water for three to four hours before planting. At planting, remove all canes except the most vigorous one. The planting hole should be about 12 inches deep and 12 inches wide. Add 4 inches of soil in the center of the hole and set the bare-root vine on top. Fill in the rest of the hole with the remaining soil, making sure to keep the soil level below the graft (the swollen area of the main stem). Water immediately after planting.\n" +
                    "\n" +
                    "Grapes grow upward and therefore need support. You can use a trellis, arbor, fence, or any post in the ground. You can decide which method fits your garden best, but be sure to have the supports in place before you plant the vines.\n" +
                    "\n" +
                    "On a vertical trellis, you will select branches from the previous year's growth to grow along the support wires. The buds along the stems will flower and set fruit. The trellis can have two or three levels, and the center stem is left to grow up to the next level. If you'd like the grapes to hang overhead from an arbor, train the vines to grow that way. You'll still shorten the branches and select a few to secure to the metal or wood arbor.\n" +
                    "\n" +
                    "Allow the grapes to grow to the top of the support in the first year, and remove the top of the cane to force it to grow laterally (parallel to the ground) from there."
        )
    )

    fun getPlantData(name: String): PlantModel {
        return plants.find { it.name == name } as PlantModel
    }
   
}

