package com.n1cks.notes.root.ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.n1cks.features.notes.noteedit.ui.screen.NoteEditScreen
import com.n1cks.features.notes.notelist.ui.screen.NoteListScreen
import com.n1cks.notes.root.child.Child
import com.n1cks.notes.root.component.RootComponent
import com.n1cks.notes.root.config.RootConfig

@Composable
fun RootContent(component: RootComponent) {
    Children(
        stack = component.childStack,
        animation = stackAnimation(slide()),
    ) {
        when (val child = it.instance) {
            is Child.NoteList -> NoteListScreen(child.component)
            is Child.NoteEdit -> NoteEditScreen(child.component)
        }
    }
}