package com.example.wordle

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wordle.FourLetterWordList.getRandomFourLetterWord
import com.example.wordle.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val word = getRandomFourLetterWord()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        keepPassingFocus()

        binding.editText14.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.length == 1) {
                    passFocusToNext(binding.editText14, binding.editText21)
                    validateRow(
                        binding.editText11,
                        binding.editText12,
                        binding.editText13,
                        binding.editText14
                    )
                }
            }

        })
        binding.editText24.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.length == 1) {
                    passFocusToNext(binding.editText14, binding.editText21)
                    validateRow(
                        binding.editText21,
                        binding.editText22,
                        binding.editText23,
                        binding.editText24
                    )
                    passFocusToNext(binding.editText24, binding.editText31)
                }
            }

        })
        binding.editText34.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.length == 1) {
                    passFocusToNext(binding.editText14, binding.editText21)
                    validateRow(
                        binding.editText31,
                        binding.editText32,
                        binding.editText33,
                        binding.editText34
                    )
                }
            }

        })
    }

    private fun validateRow(
        editText1: EditText,
        editText2: EditText,
        editText3: EditText,
        editText4: EditText
    ) {
        val letterOne = editText1.text.toString().single()
        val letterTwo = editText2.text.toString().single()
        val letterThree = editText3.text.toString().single()
        val letterFour = editText4.text.toString().single()

        val resultArray = checkGuess(word, letterOne, letterTwo, letterThree, letterFour)

//        Log.e("result", resultArray[0].toString())
//        Log.e("result", resultArray[1].toString())
//        Log.e("result", resultArray[2].toString())
//        Log.e("result", resultArray[3].toString())

        if (resultArray[0] == 'O'){
            editText1.setBackgroundColor(Color.parseColor("#79b851"))
        } else if (resultArray[0] == '+') {
            editText1.setBackgroundColor(Color.parseColor("#f3c237"))
        } else {
            editText1.setBackgroundColor(Color.parseColor("#121213"))
        }

        if (resultArray[1] == 'O'){
            editText2.setBackgroundColor(Color.parseColor("#79b851"))
        } else if (resultArray[1] == '+') {
            editText2.setBackgroundColor(Color.parseColor("#f3c237"))
        } else {
            editText2.setBackgroundColor(Color.parseColor("#121213"))
        }

        if (resultArray[2] == 'O'){
            editText3.setBackgroundColor(Color.parseColor("#79b851"))
        } else if (resultArray[2] == '+') {
            editText3.setBackgroundColor(Color.parseColor("#f3c237"))
        } else {
            editText3.setBackgroundColor(Color.parseColor("#121213"))
        }

        if (resultArray[3] == 'O'){
            editText4.setBackgroundColor(Color.parseColor("#79b851"))
        } else if (resultArray[3] == '+') {
            editText4.setBackgroundColor(Color.parseColor("#f3c237"))
        } else {
            editText4.setBackgroundColor(Color.parseColor("#121213"))
        }

        if (resultArray[0] == 'O' && resultArray[1] == 'O' && resultArray[2] == 'O' && resultArray[3] == 'O') {
            binding.guessText.text = "Congratulations, you guessed the right word!"
            binding.guessText.visibility = View.VISIBLE
        }

        if (editText4.id == R.id.editText34) {
            binding.guessText.text = "The answer is $word"
            binding.guessText.visibility = View.VISIBLE
            Toast.makeText(
                applicationContext,
                "Sorry, you didn't guess the word.",
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    private fun keepPassingFocus() {
        passFocusToNext(binding.editText11, binding.editText12)
        passFocusToNext(binding.editText12, binding.editText13)
        passFocusToNext(binding.editText13, binding.editText14)

        passFocusToNext(binding.editText21, binding.editText22)
        passFocusToNext(binding.editText22, binding.editText23)
        passFocusToNext(binding.editText23, binding.editText24)

        passFocusToNext(binding.editText31, binding.editText32)
        passFocusToNext(binding.editText32, binding.editText33)
        passFocusToNext(binding.editText33, binding.editText34)

    }

    private fun passFocusToNext(editText1: EditText, editText2: EditText) {
        editText1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.length == 1) {
                    editText2.requestFocus()
                }
            }

        })
    }

    fun checkGuess(answer: String, guessOne: Char, guessTwo: Char, guessThree: Char, guessFour: Char): CharArray {
        val result = CharArray(4) {'*'}
        val dict = mutableMapOf<Char, Int>()
        for (char in answer){
            dict[char] = dict.getOrDefault(char, 0) + 1
        }
        var g = guessOne
        if (answer[0] == g) {
            result[0] = 'O'
            dict[g] = dict.getOrDefault(g, 0) - 1
            if (dict.getOrDefault(g, 0) == 0) {
                dict.remove(g)
            }
        }
        g = guessTwo
        if (answer[1] == g) {
            result[1] = 'O'
            dict[g] = dict.getOrDefault(g, 0) - 1
            if (dict.getOrDefault(g, 0) == 0) {
                dict.remove(g)
            }
        }
        g = guessThree
        if (answer[2] == g) {
            result[2] = 'O'
            dict[g] = dict.getOrDefault(g, 0) - 1
            if (dict.getOrDefault(g, 0) == 0) {
                dict.remove(g)
            }
        }
        g = guessFour
        if (answer[3] == g) {
            result[3] = 'O'
            dict[g] = dict.getOrDefault(g, 0) - 1
            if (dict.getOrDefault(g, 0) == 0) {
                dict.remove(g)
            }
        }

        for (i in 0..3){
            if (result[i] != '*')
                continue
            var g = guessFour
            when (i) {
                0 -> {
                    g = guessOne
                }
                1 -> {
                    g = guessTwo
                }
                2 -> {
                    g = guessThree
                }
            }
            if (dict.containsKey(g)){
                result[i] = '+'
                dict[g] = dict.getOrDefault(g, 0) - 1
                if (dict.getOrDefault(g, 0) == 0){
                    dict.remove(g)
                }
            }else{
                result[i] = 'X'
            }
        }
        return result
    }
}
