import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router'; // Para navegar mediante código
@Component({
selector: 'app-login',
standalone: true,
imports: [MatCardModule, MatInputModule, MatButtonModule], 
templateUrl: './login.html', // (o './login.html')
styleUrl: './login.css' // (o './login.css')
})
export class LoginComponent {
// Inyectamos el router para poder cambiar de pantalla
constructor(private router: Router) {}
ingresarSistema() {
// Por ahora, al hacer clic, simplemente vamos al dashboard
this.router.navigate(['/dashboard']);
}
}