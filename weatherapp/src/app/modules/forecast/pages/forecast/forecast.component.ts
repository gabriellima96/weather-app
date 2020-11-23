import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { takeWhile } from 'rxjs/operators';
import { CityForecast } from 'src/app/core/models/cityForecast';
import { CityService } from 'src/app/core/services/city.service';

@Component({
  selector: 'app-forecast',
  templateUrl: './forecast.component.html',
  styleUrls: ['./forecast.component.scss']
})
export class ForecastComponent implements OnInit, OnDestroy {

  cityForecast: CityForecast;
  alive = true;

  constructor(
    private cityService: CityService,
    private activatedRouter: ActivatedRoute,
    private router: Router,
    private toast: ToastrService
  ) { }

  ngOnInit(): void {
    const id = this.activatedRouter.snapshot.params['id'];

    this.cityService.forecastByCityId(id).pipe(takeWhile(() => this.alive)).subscribe(cityForecast => {
      this.cityForecast = cityForecast;
      this.onSuccess(`Foram encontradas ${cityForecast.forecasts.length} previsões no total de 5`);
    },
    error => {
      if (error.status = 404) {
        this.onError('Cidade não encontrada.');
        this.router.navigate(['/']);
      }

      this.onError('Ocorreu um error ao tentar buscar as previsões da cidade');
    })
  }

  onError(text: string) {
    this.toast.error(text);
  }

  onSuccess(text: string) {
    this.toast.success(text);
  }

  ngOnDestroy(): void {
    this.alive = false;
  }

}
