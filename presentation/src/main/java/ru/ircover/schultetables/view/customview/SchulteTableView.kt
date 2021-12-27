package ru.ircover.schultetables.view.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import ru.ircover.schultetables.domain.Matrix2D
import ru.ircover.schultetables.domain.SchulteTableCallback
import ru.ircover.schultetables.domain.SchulteTableCell
import ru.ircover.schultetables.util.drawTextAtCenter

class SchulteTableView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var cells: Matrix2D<SchulteTableCell> = Matrix2D.empty()
    private var callback: SchulteTableCallback? = null
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.BLACK
    }
    private val dividerPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.BLACK
    }
    private var touchedCell: Point? = null
    private val touchedCellRect = Rect()
    private val touchedCellPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.CYAN
    }

    init {
        if(isInEditMode) {
            cells = Matrix2D(
                arrayOf(
                    arrayOfTestCells(1, 2, 3, 4, 5),
                    arrayOfTestCells(6, 7, 8, 9, 10),
                    arrayOfTestCells(11, 12, 13, 14, 15),
                    arrayOfTestCells(16, 17, 18, 19, 20),
                    arrayOfTestCells(21, 22, 23, 24, 25)
                )
            )
            touchedCell = Point(1, 1)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val ratio = cells.width.toFloat() / cells.height
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        if(widthMode == MeasureSpec.UNSPECIFIED && heightMode == MeasureSpec.UNSPECIFIED) {
            //ой, да ебись как хочешь вообще
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        } else {
            var width = MeasureSpec.getSize(widthMeasureSpec)
            var height = MeasureSpec.getSize(heightMeasureSpec)
            val expectedWidth = (height * ratio).toInt()
            if(expectedWidth != width) {
                if (expectedWidth > width && widthMode != MeasureSpec.UNSPECIFIED
                        || heightMode == MeasureSpec.UNSPECIFIED) {
                    height = (width / ratio).toInt()
                    width = (height * ratio).toInt()//чтобы сгладить косяки округления
                } else {
                    width = expectedWidth
                }
            }
            setMeasuredDimension(
                MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY))
        }
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        if(canvas != null && cells.width > 0 && cells.height > 0) {
            drawDividers(canvas, cells)
            val cellWidth = width / cells.width
            val cellHeight = height / cells.height
            textPaint.textSize = cellHeight * 0.7f
            for (x in 0 until cells.width) {
                for (y in 0 until cells.height) {
                    if(touchedCell?.x == x && touchedCell?.y == y) {
                        touchedCellRect.set(cellWidth * x + 1, cellHeight * y + 1,
                            cellWidth * (x + 1), cellHeight * (y + 1))
                        canvas.drawRect(touchedCellRect, touchedCellPaint)
                    }
                    val cell = cells.get(x, y)
                    val centerX = cellWidth * (x + 0.5f)
                    val centerY = cellHeight * (y + 0.5f)
                    canvas.drawTextAtCenter(cell.text, centerX, centerY, textPaint)
                }
            }
        }
    }

    fun setCells(cells: Matrix2D<SchulteTableCell>) {
        this.cells = cells
        requestLayout()
        invalidate()
    }

    fun setCallback(callback: SchulteTableCallback) {
        this.callback = callback
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return if(event != null && cells.width > 0 && cells.height > 0) {
            val cellWidth = width / cells.width
            val cellHeight = height / cells.height
            val eventCell = Point((event.x / cellWidth).toInt(), (event.y / cellHeight).toInt())
            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    touchedCell = eventCell
                    invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    if(eventCell == touchedCell) {
                        callback?.click(cells.get(eventCell.x, eventCell.y))
                    }
                    touchedCell = null
                    invalidate()
                    performClick()
                }
                MotionEvent.ACTION_CANCEL -> {
                    touchedCell = null
                    invalidate()
                }
            }
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    private fun drawDividers(canvas: Canvas, matrix: Matrix2D<SchulteTableCell>) {
        val cellWidth = width / matrix.width
        val cellHeight = height / matrix.height
        for (x in 1 until matrix.width) {
            val dividerX = (cellWidth * x).toFloat()
            canvas.drawLine(dividerX, 0f, dividerX, height.toFloat(), dividerPaint)
        }
        for (y in 1 until matrix.height) {
            val dividerY = (cellHeight * y).toFloat()
            canvas.drawLine(0f, dividerY, width.toFloat(), dividerY, dividerPaint)
        }
    }

    private fun arrayOfTestCells(vararg numbers: Int): Array<SchulteTableCell> {
        return numbers.map { SchulteTableCell(it.toString(), it) }
            .toTypedArray()
    }}