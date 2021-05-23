package info.introvertische.clbyi

class clbyi {
    private val manager = Manager()
    private val engine = Engine(manager.lexicalAnalyzer, manager.mathManager, manager.mathValidator)

    fun toStart(mathExpression: String) : String {
        return engine.toStart(mathExpression)
    }
}