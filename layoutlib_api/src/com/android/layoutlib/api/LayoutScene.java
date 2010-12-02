/*
 * Copyright (C) 2010 The Android Open Source Project
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

package com.android.layoutlib.api;

import static com.android.layoutlib.api.SceneResult.SceneStatus.NOT_IMPLEMENTED;

import com.android.layoutlib.api.SceneResult.SceneStatus;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * An object allowing interaction with an Android layout.
 *
 * This is returned by {@link LayoutBridge#createScene(SceneParams)}.
 * and can then be used for subsequent actions on the layout.
 *
 * @since 5
 *
 */
public class LayoutScene {

    public interface IAnimationListener {
        /**
         * Called when a new animation frame is available for display.
         *
         * <p>The {@link LayoutScene} object is provided as a convenience. It should be queried
         * for the image through {@link LayoutScene#getImage()}.
         *
         * <p>If no {@link IImageFactory} is used, then each new animation frame will be rendered
         * in its own new {@link BufferedImage} object. However if an image factory is used, and it
         * always re-use the same object, then the image is only guaranteed to be valid during
         * this method call. As soon as this method return the image content will be overridden
         * with new drawing.
         *
         */
        void onNewFrame(LayoutScene scene);

        /**
         * Called when the animation is done playing.
         */
        void done(SceneResult result);

        /**
         * Return true to cancel the animation.
         */
        boolean isCanceled();
    }

    /**
     * Returns the last operation result.
     */
    public SceneResult getResult() {
        return NOT_IMPLEMENTED.getResult();
    }

    /**
     * Returns the {@link ViewInfo} object for the top level view.
     * <p>
     *
     * This is reset to a new instance every time {@link #render()} is called and can be
     * <code>null</code> if the call failed (and the method returned a {@link SceneResult} with
     * {@link SceneStatus#ERROR_UNKNOWN} or {@link SceneStatus#NOT_IMPLEMENTED}.
     * <p/>
     * This can be safely modified by the caller.
     */
    public ViewInfo getRootView() {
        return null;
    }

    /**
     * Returns the rendering of the full layout.
     * <p>
     * This is reset to a new instance every time {@link #render()} is called and can be
     * <code>null</code> if the call failed (and the method returned a {@link SceneResult} with
     * {@link SceneStatus#ERROR_UNKNOWN} or {@link SceneStatus#NOT_IMPLEMENTED}.
     * <p/>
     * This can be safely modified by the caller.
     */
    public BufferedImage getImage() {
        return null;
    }


    /**
     * Returns a map of (XML attribute name, attribute value) containing only default attribute
     * values, for the given view Object.
     * @param viewObject the view object.
     * @return a map of the default property values or null.
     */
    public Map<String, String> getDefaultViewPropertyValues(Object viewObject) {
        return null;
    }

    /**
     * Re-renders the layout as-is.
     * In case of success, this should be followed by calls to {@link #getRootView()} and
     * {@link #getImage()} to access the result of the rendering.
     *
     * This is equivalent to calling <code>render(SceneParams.DEFAULT_TIMEOUT)</code>
     *
     * @return a {@link SceneResult} indicating the status of the action.
     */
    public SceneResult render() {
        return render(SceneParams.DEFAULT_TIMEOUT);
    }

    /**
     * Re-renders the layout as-is, with a given timeout in case other renderings are being done.
     * In case of success, this should be followed by calls to {@link #getRootView()} and
     * {@link #getImage()} to access the result of the rendering.
     *
     * The {@link LayoutBridge} is only able to inflate or render one layout at a time. There
     * is an internal lock object whenever such an action occurs. The timeout parameter is used
     * when attempting to acquire the lock. If the timeout expires, the method will return
     * {@link SceneStatus#ERROR_TIMEOUT}.
     *
     * @param timeout timeout for the rendering, in milliseconds.
     *
     * @return a {@link SceneResult} indicating the status of the action.
     */
    public SceneResult render(long timeout) {
        return NOT_IMPLEMENTED.getResult();
    }

    /**
     * Sets the value of a given property on a given object.
     * <p/>
     * This does nothing more than change the property. To render the scene in its new state, a
     * call to {@link #render()} is required.
     * <p/>
     * Any amount of actions can be taken on the scene before {@link #render()} is called.
     *
     * @param objectView
     * @param propertyName
     * @param propertyValue
     *
     * @return a {@link SceneResult} indicating the status of the action.
     */
    public SceneResult setProperty(Object objectView, String propertyName, String propertyValue) {
        return NOT_IMPLEMENTED.getResult();
    }

    /**
     * Inserts a new child in a ViewGroup object, and renders the result.
     * <p/>
     * The child is first inflated and then added to its new parent, at the given <var>index<var>
     * position. If the <var>index</var> is -1 then the child is added at the end of the parent.
     * <p/>
     * If an animation listener is passed then the rendering is done asynchronously and the
     * result is sent to the listener.
     * If the listener is null, then the rendering is done synchronously.
     *
     * The child stays in the view hierarchy after the rendering is done. To remove it call
     * {@link #removeChild(Object, int)}.
     *
     * The returned {@link SceneResult} object will contain the android.view.View object for
     * the newly inflated child. It is accessible through {@link SceneResult#getData()}.
     *
     * @param parentView the parent View object to receive the new child.
     * @param childXml an {@link IXmlPullParser} containing the content of the new child.
     * @param index the index at which position to add the new child into the parent. -1 means at
     *             the end.
     * @param listener an optional {@link IAnimationListener}.
     *
     * @return a {@link SceneResult} indicating the status of the action.
     */
    public SceneResult insertChild(Object parentView, IXmlPullParser childXml, int index,
            IAnimationListener listener) {
        return NOT_IMPLEMENTED.getResult();
    }

    /**
     * Move a new child to a different ViewGroup object.
     * <p/>
     * The child is first removed from its current parent, and then added to its new parent, at the
     * given <var>index<var> position. In case the <var>parentView</var> is the current parent of
     * <var>childView</var> then the index must be the value with the <var>childView</var> removed
     * from its parent. If the <var>index</var> is -1 then the child is added at the end of
     * the parent.
     * <p/>
     * If an animation listener is passed then the rendering is done asynchronously and the
     * result is sent to the listener.
     * If the listener is null, then the rendering is done synchronously.
     *
     * The child stays in the view hierarchy after the rendering is done. To remove it call
     * {@link #removeChild(Object, int)}.
     *
     * @param parentView the parent View object to receive the child. Can be the current parent
     *             already.
     * @param childView the view to move.
     * @param index the index at which position to add the new child into the parent. -1 means at
     *             the end.
     * @param listener an optional {@link IAnimationListener}.
     *
     * @return a {@link SceneResult} indicating the status of the action.
     */
    public SceneResult moveChild(Object parentView, Object childView, int index,
            IAnimationListener listener) {
        return NOT_IMPLEMENTED.getResult();
    }

    /**
     * Removes a child from a ViewGroup object.
     * <p/>
     * This does nothing more than change the layout. To render the scene in its new state, a
     * call to {@link #render()} is required.
     * <p/>
     * Any amount of actions can be taken on the scene before {@link #render()} is called.
     *
     * @param childView the view object to remove from its parent
     * @param listener an optional {@link IAnimationListener}.
     *
     * @return a {@link SceneResult} indicating the status of the action.
     */
    public SceneResult removeChild(Object childView, IAnimationListener listener) {
        return NOT_IMPLEMENTED.getResult();
    }

    /**
     * Starts playing an given animation on a given object.
     * <p/>
     * The animation playback is asynchronous and the rendered frame is sent vi the
     * <var>listener</var>.
     *
     * @param targetObject the view object to animate
     * @param animationName the name of the animation (res/anim) to play.
     * @param listener the listener callback.
     *
     * @return a {@link SceneResult} indicating the status of the action.
     */
    public SceneResult animate(Object targetObject, String animationName,
            boolean isFrameworkAnimation, IAnimationListener listener) {
        return NOT_IMPLEMENTED.getResult();
    }

    /**
     * Discards the layout. No more actions can be called on this object.
     */
    public void dispose() {
    }
}
