/*
 * Copyright (C) 2016 Jake Wharton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package retrofit2.adapter.rxjava2;

import java.lang.reflect.Type;


import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.plugins.RxJavaPlugins;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;

final class RxJava2CallAdapter2<R> implements CallAdapter<R, Object> {
    private final Type responseType;
    private final Scheduler schedulerSubscribeOn;
    private final Scheduler schedulerObserveOn;
    private final boolean isAsync;
    private final boolean isResult;
    private final boolean isBody;
    private final boolean isGithubPaging;
    private final boolean isFlowable;
    private final boolean isSingle;
    private final boolean isMaybe;
    private final boolean isCompletable;

    RxJava2CallAdapter2(Type responseType, Scheduler schedulerSubscribeOn, Scheduler schedulerObserveOn, boolean isAsync,
                        boolean isResult, boolean isBody, boolean isGithubPaging, boolean isFlowable, boolean isSingle, boolean isMaybe,
                        boolean isCompletable) {
        this.responseType = responseType;
        this.schedulerSubscribeOn = schedulerSubscribeOn;
        this.schedulerObserveOn = schedulerObserveOn;
        this.isAsync = isAsync;
        this.isResult = isResult;
        this.isBody = isBody;
        this.isGithubPaging = isGithubPaging;
        this.isFlowable = isFlowable;
        this.isSingle = isSingle;
        this.isMaybe = isMaybe;
        this.isCompletable = isCompletable;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public Object adapt(Call<R> call) {
        Observable<Response<R>> responseObservable = isAsync
                ? new CallEnqueueObservable<>(call)
                : new CallExecuteObservable<>(call);

        Observable<?> observable;
        if (isResult) {
            observable = new ResultObservable<>(responseObservable);
        } else if (isBody) {
            observable = new BodyObservable<>(responseObservable);
        } else if (isGithubPaging) {
            observable = new GitHubListOnSubscribe<>(responseObservable);
        } else {
            observable = responseObservable;
        }

        if (schedulerSubscribeOn != null) {
            observable = observable.subscribeOn(schedulerSubscribeOn);
        }

        if (schedulerObserveOn != null) {
            observable = observable.observeOn(schedulerObserveOn);
        }

        if (isFlowable) {
            return observable.toFlowable(BackpressureStrategy.LATEST);
        }
        if (isSingle) {
            return observable.singleOrError();
        }
        if (isMaybe) {
            return observable.singleElement();
        }
        if (isCompletable) {
            return observable.ignoreElements();
        }
        return RxJavaPlugins.onAssembly(observable);
    }
}
