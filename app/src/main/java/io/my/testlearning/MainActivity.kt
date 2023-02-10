package io.my.testlearning

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.io.navigation.AndroidPresenterStoreOwner
import com.io.navigation.PresenterCompositionLocalProvider
import com.io.navigation.presenter
import io.my.testlearning.ui.presenter.ShoppingPresenter
import io.my.testlearning.ui.theme.TestLearningTheme
import io.my.testlearning.util.BasePresenterAdapter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val owner = AndroidPresenterStoreOwner<String>(BasePresenterAdapter())

        setContent {
            TestLearningTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PresenterCompositionLocalProvider(
                        owner = owner
                    ) {
                        val presenter: ShoppingPresenter = presenter(factory = presenterFactory())


                    }
                }
            }
        }
    }
}