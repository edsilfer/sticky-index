#Sticky Index Library
This repository contains the source code for a component that implements the " Sticky Letter Index" such as it is presented in the Android Contact App from Android Lollipop (API 21) on. Please feel free to use it as well as enhance it

**Dont't forget to read the P.S. at the [how to use](#how-to-use) section**

# Table of Contents
* [Introduction](#intro)
* [Showcase](#showcase)
* [How to use](#how-to-use)
* [TODO List](#to-do)
* [Team Members](#team-members)


# <a name="intro"></a>Introduction
You got an smartphone with Android API 21+, took a look in the Contact app and got excited by those sticky letter that scrolls with the list in a beautiful manner? Well, this library intend to provide you, dear developer, the same component.

# <a name="showcase"></a>Showcase
Check below the library in a Contact app context:

![Demo](https://github.com/edsilfer/sticky-index/blob/master/app/demo/sticky_index_demo.gif)

# <a name="how-to-use"></a>How to use
First of all, add the following dependencies to your build.gradle:

```
repositories {
    maven {
        url "https://jitpack.io"
    }
}

dependencies {
    compile 'com.github.edsilfer:sticky-index:1.0.0'
}

```

Then, add this component to your XML layout (usualy, right after the RecyclerView):

```
<br.com.stickyindex.StickyIndex
        android:id="@+id/sticky_index_container"
        android:layout_width="wrap_content"
        android:layout_height="match_parent" />
```

By last, initialize it in the container main class. Also add the dataSet (a char array that will contain the indexes). This array must have the same size as the main list, with each row corresponding to the element that it will be associated). Finally, set the corresponding RecyclerVIew that will control the scroll movement:

```
// Creates index viewer
        StickyIndex indexContainer = (StickyIndex) this.findViewById(R.id.sticky_index_container);
        // INSERT CHAR LIST
        indexContainer.setDataSet(getIndexList(myContacts));
        indexContainer.setReferenceList(recyclerView);
```

**P.S.: so far the row height is set to 64dp, you should set your reference RecyclerView row height to the same size in order to maintain consistency between the two lists. Different size will lead to layout failures.**

# <a name="to-do"></a>TODO List
* Upload library into JCenter/Maven;
* Make index list background transparent in order to correct the detail when user pushes down the list in the top or bottom of it;
* Add attributes gather from the XML in order to set the row height, text size, style and color;
* Try to enhance the flying animation;

# <a name="team-members"></a>Team Members
* "Fernandes S. Edgar" <fernandes.s.edgar@gmail.com>
