package com.example.enrollmentplanner.features.form.ultil

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val phoneMask = buildString {
            text.text.forEachIndexed { index, c ->
                when (index) {
                    0 -> append("($c")
                    1 -> append("$c) ")
                    6 -> append("$c-")
                    else -> append(c)
                }
            }
        }

        return TransformedText(AnnotatedString(phoneMask), PhoneOffsetMapping)
    }

    object PhoneOffsetMapping : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return when {
                offset > 6 -> offset + 4
                offset > 1 -> offset + 3
                offset > 0 -> offset + 1
                else -> offset
            }
        }

        override fun transformedToOriginal(offset: Int): Int {
            return when {
                offset >= 11 -> offset - 4
                offset >= 5 -> offset - 3
                offset == 4 -> offset - 3
                offset >= 2 -> offset - 1
                else -> offset
            }
        }

    }

}

fun cpfVisualTransformation(): VisualTransformation = VisualTransformation { text ->
    val cpfMask = text.text.mapIndexed { index, c ->
        when (index) {
            2 -> "$c."
            5 -> "$c."
            8 -> "$c-"
            else -> c
        }
    }.joinToString(separator = "")

    TransformedText(
        AnnotatedString(cpfMask),
        object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset > 8 -> offset + 3
                    offset > 5 -> offset + 2
                    offset > 2 -> offset + 1
                    else -> offset
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset > 8 -> offset - 3
                    offset > 5 -> offset - 2
                    offset > 2 -> offset - 1
                    else -> offset
                }
            }
        }
    )
}

class DateVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {

        val dateMask = text.text.mapIndexed { index, c ->
            when (index) {
                1 -> "$c/"
                3 -> "$c/"
                else -> c
            }
        }.joinToString(separator = "")

        return TransformedText(
            AnnotatedString(dateMask),
            DateOffsetMapping
        )
    }

    object DateOffsetMapping : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return when {
                offset > 3 -> offset + 2
                offset > 1 -> offset + 1
                else -> offset
            }
        }

        override fun transformedToOriginal(offset: Int): Int {
            return when {
                offset > 3 -> offset - 2
                offset > 1 -> offset - 1
                else -> offset
            }
        }

    }

}
