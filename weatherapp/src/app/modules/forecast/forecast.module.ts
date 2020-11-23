import { NgModule } from '@angular/core';
import { ForecastComponent } from './pages/forecast/forecast.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { ForecastRoutingModule } from './forecast-routing.module';

@NgModule({
  declarations: [ForecastComponent],
  imports: [
    SharedModule,
    ForecastRoutingModule
  ]
})
export class ForecastModule { }
