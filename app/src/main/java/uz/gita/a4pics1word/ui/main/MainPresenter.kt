package uz.gita.a4pics1word.ui.main

class MainPresenter(private val view: MainContract.View) : MainContract.Presenter {
    private val model: MainContract.Model = MainModel()
    override fun clickPlayBtn() {
        if (model.getLevel() > model.getChangedLevel() && model.getChangedLevel() != -1) {
            view.showDialogLevel {
                if (it) {
                    model.saveLevel(model.getChangedLevel())
                    model.saveChangedLevel(model.getLevel())
                    model.removeState()
                    view.openGameScreen()
                }
            }
        } else if (model.getLevel() < model.getChangedLevel() && model.getChangedLevel() != -1 && model.getChangedLevel() <= model.getLevelReached()) {
            view.showDialogLevel {
                if (it) {
                    model.saveLevel(model.getChangedLevel())
                    model.saveChangedLevel(model.getLevel())
                    model.removeState()
                    view.openGameScreen()
                }
            }
        } else if (model.getLevel() >= model.getQuestionSize()) view.showToast("New level  soon!!!")
        else if (model.getLevel() > model.getLevelReached() || model.getChangedLevel() > model.getLevelReached()) view.showToast("You have not reached this level yet!")
        else view.openGameScreen()
    }

    override fun clickSettingsBtn() {

    }

    override fun clickInfo() {
        view.showInfoDialog()
    }

    override fun loadData() {
        view.loadData(model.getCurrQuestion(), model.getCoin(), model.getLevel())
    }

    override fun currLevel(level: Int) {
        model.setCurrLevel(level)
    }

    override fun getLevel(): Int = model.getLevel() + 1
}