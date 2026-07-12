import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login';
import { Dashboard } from './pages/dashboard/dashboard';
export const routes: Routes = [
{ path: '', redirectTo: 'login', pathMatch: 'full' }, // Ruta por defecto
{ path: 'login', component: LoginComponent },
{ path: 'dashboard', component: Dashboard },
];