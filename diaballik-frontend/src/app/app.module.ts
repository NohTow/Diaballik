import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { BoardComponentComponent } from './board-component/board-component.component';
import { MenuComponent } from './menu/menu.component';

const appRoutes: Routes = [
  { path: '',
    redirectTo: '/config',
    pathMatch: 'full'
  },
  {
    path: 'menu',
    component: MenuComponent
  },
  {
    path: 'board-component',
    component: BoardComponentComponent
  }
];

@NgModule({
  declarations: [
    AppComponent,
    BoardComponentComponent,
    MenuComponent
  ],
  imports: [
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: false }
    ),
    BrowserModule,
    HttpClientModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
