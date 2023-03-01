package io.my.testlearning

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode
import io.my.testlearning.util.TestTags

class ComposeShowItemsScreen(semanticProvider: SemanticsNodeInteractionsProvider): ComposeScreen<ComposeShowItemsScreen>(
    semanticsProvider = semanticProvider,
    viewBuilderAction = { hasTestTag(TestTags.showItemsScreen) }
) {

    val sumField: KNode = child {
        hasTestTag(TestTags.sumDisplay)
    }

    val addItem: KNode = child {
        hasTestTag(TestTags.addItemTag)
    }

}

class ComposeAddItemScreen(semanticProvider: SemanticsNodeInteractionsProvider): ComposeScreen<ComposeAddItemScreen>(
    semanticsProvider = semanticProvider,
    viewBuilderAction = { hasTestTag(TestTags.addItemScreen) }
) {

    val saveItem: KNode = child {
        hasTestTag(TestTags.saveItem)
    }
    val inputTitle: KNode = child {
        hasTestTag(TestTags.inputTitle)
    }
    val inputAmount: KNode = child {
        hasTestTag(TestTags.inputAmount)
    }
    val inputPrice: KNode = child {
        hasTestTag(TestTags.inputPrice)
    }
    val inputImage: KNode = child {
        hasTestTag(TestTags.inputImage)
    }
}

class ComposeChoiseImage(semanticProvider: SemanticsNodeInteractionsProvider): ComposeScreen<ComposeChoiseImage>(
    semanticsProvider = semanticProvider,
    viewBuilderAction = { hasTestTag(TestTags.choiseImagePop) }
) {

    val choiseImage: KNode = child {
        hasTestTag(TestTags.choiseImage)
    }
}