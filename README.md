## sticky-index & fast-scroller
Provides _sticky-index_ and _fast-scroller_ UI components

### Showcase

<p align="center">
    <img src="art/vertical-demo.gif" align="center" width=300>
</p>

### How to use
1. Import the dependencies

```
repositories {
    maven {
        url "https://jitpack.io"
    }
}

dependencies {
    compile 'com.github.edsilfer:sticky-index:1.3.0'
}

```

2. Add the component to your XML layout, right after the `RecyclerView`:

```xml
<br.com.stickyindex.view.StickyIndex
    android:id="@+id/stickyIndex"
    android:layout_width="80dp"
    android:layout_height="wrap_content"
    android:textSize="20sp"
    android:textStyle="bold"
    app:rowHeight="64dp"
    app:stickyWidth="60dp" />

<android.support.v7.widget.RecyclerView
    android:id="@+id/recyclerView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />

 <br.com.stickyindex.view.FastScroller
    android:id="@+id/fastScroller"
    android:layout_width="wrap_content"
    android:layout_height="match_parent"
    android:layout_alignParentEnd="true"
    android:layout_marginBottom="10dp"
    android:layout_marginTop="10dp" />
```

_PS: `FastScroller` is optional_

3. Customize `sitcky-index`:
You can customizing the stick-index with the following arguments:
 * ```xml android:textSize```: changes the index list text size;
 * ```xml android:textStyle```: changes the index list text style;
 * ```xml android:textColor```: changes the index list test color;
 * ```xml app:rowHeight```: changes the index list row height;
 * ```xml app:stickyWidth```: changes the LinearLayout width that wrappers the text - which, by its turn, it is centralized inside it. Use this atribute to control the distance of the index list from the corner of its parent.

**The last attribute ```xml android:rowHeight``` is mandatory. `sticky-index` consists of a `RecyclerView` which must match the reference one (in terms of `ScrollListener` and `rowHeight`). Having different `rowHeights` WILL lead to layout failures.**

4. initialize it in the container main class. The `dataSet` (a char array that will contain the indexes) must have the same size as the main list, with each row corresponding to the element that it will be associated). Finally, set the reference `RecyclerView` that will control the scroll movement:

```java
    // Acquire component's reference
    StickyIndex stickyIndex = findViewById<StickyIndex>(R.id.stickyIndex);
    FastScroller fastScroller = findViewById<FastScroller>(R.id.fastScroller);
    // Binds the RecyclerViews to synchronize the scroll event between the them
    stickyIndex.bindRecyclerView(recyclerView);
    fastScroller.bindRecyclerView(recyclerView);
    // Adds the sticky index content to the stickyIndex. This must be called whenever the content changes
    stickyIndex.refresh(convertToIndexList(contacts))
```

### License
Copyright 2018 Edgar da Silva Fernandes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
