import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { provideHttpClient } from '@angular/common/http'; // <-- Importar
export const appConfig: ApplicationConfig = {
providers: [provideRouter(routes), provideHttpClient()] // <-Añadir
};

