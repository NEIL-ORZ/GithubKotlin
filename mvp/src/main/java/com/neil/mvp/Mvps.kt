package com.neil.mvp

/**
 * @USER NEIL.Z
 * @TIME 2020/6/9 0009 15:41
 * @DESC
 */
interface IPresenter<out View : IMvpView<IPresenter<View>>> : ILifecycle {
    val view: View
}

interface IMvpView<out Presenter : IPresenter<IMvpView<Presenter>>> : ILifecycle {
    val presenter: Presenter
}