import { City } from './city';
import { Forecast } from './forecast';

export interface CityForecast {
  city: City;
  forecasts: Forecast[];
}
