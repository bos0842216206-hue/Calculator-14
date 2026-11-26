package com.example.calculator_14

import android.os.Bundle
import android.view.Display
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {


    //ประกาศตัวแปร

    private lateinit var display: TextView //แสดงผลตัวเลข / แสดงผลลัพธ์
    private var current = ""//ค่าปัจจุบันที่กำลังพิมพ์
    private var operator = ""//เครื่องหมายที่เราเลือก(+,-,*,/)
    private var firsNumber = 0.0//ตัวเลขตัวแรก
    private var secondNumder = 0.0//ตัวเลขตัวที่2
    private var result = 0.0//ผลลัพธ์
    private var isNweOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //
        display = findViewById(R.id.txtDisplay)

        //ตัวแปรเก็บตัวเลข
        val numberButton = listOf(
            R.id.btn0,
            R.id.btn1,
            R.id.btn2,
            R.id.btn3,
            R.id.btn4,
            R.id.btn5,
            R.id.btn6,
            R.id.btn7,
            R.id.btn8,
            R.id.btn9,
        )
        for (id in numberButton){
            findViewById<Button>(id).setOnClickListener {
                numberClicked((it as Button).text.toString())

            }
        }

        //ปุ่มเครื่องหมายพื้นฐาน
        findViewById<Button>(R.id.btnMod).setOnClickListener { operatorClicked("%") }
        findViewById<Button>(R.id.btnPlus).setOnClickListener { operatorClicked("+") }
        findViewById<Button>(R.id.btnMinus).setOnClickListener { operatorClicked("-") }
        findViewById<Button>(R.id.btnMultipiy).setOnClickListener { operatorClicked("*") }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { operatorClicked("/") }

        //ปุ่มเครื่องหมายพิเศษ
        findViewById<Button>(R.id.btnEqual).setOnClickListener { calculateResult() }
        findViewById<Button>(R.id.btnclear).setOnClickListener { clearAll() }
        findViewById<Button>(R.id.btnComma).setOnClickListener { addDot() }
        findViewById<Button>(R.id.btnPlus_minus).setOnClickListener { toggleSing() }
        findViewById<Button>(R.id.btnDelete).setOnClickListener { backSpace() }
    }
    //เมื่อกดปุ่มตัวเลข
    private fun numberClicked(value: String)
    {
        if (isNweOperation){ //ถ้าเป็นการเริ่มใหม่ ให้ทำการล้างหน้าจอก่อน
            display.text = ""
            isNweOperation = false
        }
        current += value //เพิ่มค่าตัวเลขที่ต่อท้าย
        display.text = current //ให้แสดงค่าบนหน้าจอ
    }
    //
    private  fun operatorClicked(op: String)
    {
        if (current.isNotEmpty()){ //ต้องมีตัวเลขก่อนถึงจะเลือกเครื่องหมายได้
            firsNumber = current.toDouble()
            operator = op
            current = ""//เคลียร์หน้าจอเพื่อรับตัวเลขถัดไป
        }
    }
    private  fun calculateResult()
    {
        if (current.isNotEmpty())
        {
            secondNumder = current.toDouble()
            result = when(operator)
            {
                "+" -> firsNumber + secondNumder
                "-" -> firsNumber - secondNumder
                "*" -> firsNumber * secondNumder
                "%" -> firsNumber % secondNumder
                "/" -> if (secondNumder != 0.0)
                    firsNumber / secondNumder  else Double.NaN
                else -> 0.0
            }
            //ถ้าหารด้วย 0 แสดงข้อความว่า error "Can noy devided by zero"
            display.text = if (result.isNaN())"Can not devided by zero" else result.toString()
            current = result.toString()
            isNweOperation = true
        }
    }
    //เมื่อต้องการเคลียร์ข้อมูล
    private fun clearAll(){
        current = ""
        operator = ""
        firsNumber = 0.0
        secondNumder = 0.0
        result = 0.0
        display.text = "0"
        isNweOperation = true
    }
    //เพิ่มปุ่มทศนิยมในชุดตัวเลข
    private fun addDot()
    {
        if (!current.contains("."))
        {
            if (current.isEmpty()) current = "0"
            current += "."
            display.text = current
        }
    }
    //ปุ่มเปลี่ยนเครื่องหมาย (+) -> (-), (-) -> (+)
    private fun toggleSing()
    {
        if (current.isNotEmpty() && current != "0")
        {
            current = if (current.startsWith("-"))
            {
                current.substring( 1)
            }else{
                "-$current"
            }
            display.text = current
        }
    }
    //ปุ่มลบทีล่ะตัว
    private fun backSpace()
    {
        if (isNweOperation) return

        if (current.isNotEmpty())
        {
            current = current.dropLast(1)
            if (current == "-" || current.isEmpty())
            {
                current = ""
                display.text = "0"
            }else{
                display.text = current
            }

        }
    }
}