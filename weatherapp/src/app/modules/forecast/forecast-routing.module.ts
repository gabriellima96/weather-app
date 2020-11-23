import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ForecastComponent } from './pages/forecast/forecast.component';

const routes: Routes = [
  {
    path: '',
    component: ForecastComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ForecastRoutingModule { }
