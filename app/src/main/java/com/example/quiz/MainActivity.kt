package com.example.quiz

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var textQuestion: TextView
    private lateinit var buttonOption1: Button
    private lateinit var buttonOption2: Button
    private lateinit var buttonOption3: Button
    private lateinit var buttonOption4: Button
    private lateinit var textScore: TextView

    private var currentQuestionIndex = 0
    private var score = 0

    private val quizQuestions = listOf(
        QuizQuestion(
            "Сколько весит стандартная олимпийская штанга (гриф)?",
            listOf("10 кг", "15 кг", "20 кг", "25 кг"),
            2
        ),
        QuizQuestion(
            "Какой мышечной группы НЕТ?",
            listOf("Бицепс", "Трицепс", "Квадрицепс", "Пентасепс"),
            3
        ),
        QuizQuestion(
            "Сколько калорий в среднем сжигает 1 час силовой тренировки?",
            listOf("200–300", "400–600", "700–900", "1000+"),
            1
        ),
        QuizQuestion(
            "Что означает '1RM' в тренировках?",
            listOf("1 повторение с максимальным весом", "1 минута отдыха", "1 круг упражнений", "1 раз в месяц"),
            0
        ),
        QuizQuestion(
            "Какой из этих упражнений НЕ нагружает грудные мышцы?",
            listOf("Жим лёжа", "Отжимания", "Подтягивания", "Разводка гантелей"),
            2
        ),
        QuizQuestion(
            "Сколько дней в неделю рекомендуется тренировать одну мышечную группу?",
            listOf("1 раз", "2–3 раза", "Каждый день", "Только по выходным"),
            1
        ),
        QuizQuestion(
            "Какой из этих напитков чаще всего пьют для восстановления после тренировки?",
            listOf("Кола", "Энергетик", "Протеиновый коктейль", "Сок"),
            2
        ),
        QuizQuestion(
            "Что такое 'суперсет'?",
            listOf("Два упражнения подряд без отдыха", "Самый тяжёлый вес", "Тренировка с партнёром", "Упражнение на пресс"),
            0
        ),
        QuizQuestion(
            "Какой вес гантелей считается 'лёгким' для начинающего мужчины?",
            listOf("1–2 кг", "3–5 кг", "6–8 кг", "9–12 кг"),
            1
        ),
        QuizQuestion(
            "Сколько белка в день рекомендуется потреблять при наборе мышечной массы?",
            listOf("0.8 г/кг", "1.2–1.7 г/кг", "2.5 г/кг", "3+ г/кг"),
            1
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        initViews()
        loadQuestion()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initViews() {
        textQuestion = findViewById(R.id.textQuestion)
        buttonOption1 = findViewById(R.id.buttonOption1)
        buttonOption2 = findViewById(R.id.buttonOption2)
        buttonOption3 = findViewById(R.id.buttonOption3)
        buttonOption4 = findViewById(R.id.buttonOption4)
        textScore = findViewById(R.id.textScore)

        buttonOption1.setOnClickListener { checkAnswer(0) }
        buttonOption2.setOnClickListener { checkAnswer(1) }
        buttonOption3.setOnClickListener { checkAnswer(2) }
        buttonOption4.setOnClickListener { checkAnswer(3) }
    }

    private fun loadQuestion() {
        if (currentQuestionIndex < quizQuestions.size) {
            val current = quizQuestions[currentQuestionIndex]
            textQuestion.text = current.question
            buttonOption1.text = current.options[0]
            buttonOption2.text = current.options[1]
            buttonOption3.text = current.options[2]
            buttonOption4.text = current.options[3]

            textScore.text = getString(R.string.score_text, score, quizQuestions.size)
        } else {
            showResult()
        }
    }

    private fun checkAnswer(selectedOption: Int) {
        val current = quizQuestions[currentQuestionIndex]
        if (selectedOption == current.correctAnswerIndex) {
            score++
            Toast.makeText(this, R.string.answer_correct, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, R.string.answer_incorrect, Toast.LENGTH_SHORT).show()
        }
        currentQuestionIndex++
        loadQuestion()
    }

    private fun showResult() {
        val percentage = (score * 100) / quizQuestions.size
        val message = when {
            percentage >= 90 -> getString(R.string.result_excellent)
            percentage >= 70 -> getString(R.string.result_good)
            percentage >= 50 -> getString(R.string.result_ok)
            else -> getString(R.string.result_poor)
        }

        textQuestion.text = getString(R.string.result_summary, score, quizQuestions.size, message)

        buttonOption1.visibility = Button.VISIBLE
        buttonOption1.text = getString(R.string.restart)
        buttonOption1.setOnClickListener { restartQuiz() }
        buttonOption2.visibility = Button.GONE
        buttonOption3.visibility = Button.GONE
        buttonOption4.visibility = Button.GONE
        textScore.visibility = TextView.GONE
    }

    private fun restartQuiz() {
        score = 0
        currentQuestionIndex = 0
        buttonOption1.setOnClickListener { checkAnswer(0) }
        buttonOption2.visibility = Button.VISIBLE
        buttonOption3.visibility = Button.VISIBLE
        buttonOption4.visibility = Button.VISIBLE
        textScore.visibility = TextView.VISIBLE
        loadQuestion()
    }
}

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)