import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { OktaAuthService } from '@okta/okta-angular';
import { LoginDto } from './dto/login.dto';
import { SignupDto, SignupResponse } from './dto/signup.dto';
import { environment as env } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private oktaAuth: OktaAuthService, private http: HttpClient) {}

  async signup(signupDto: SignupDto): Promise<SignupResponse> {
    return await this.http
      .post<SignupResponse>(`${env.apiUrl}/auth/signup`, signupDto)
      .toPromise();
  }

  async login(loginDto: LoginDto): Promise<void> {
    try {
      const transaction = await this.oktaAuth.signInWithCredentials({
        username: loginDto.email,
        password: loginDto.password,
      });

      if (transaction.status === 'SUCCESS') {
        this.oktaAuth.token.getWithRedirect({
          sessionToken: transaction.sessionToken,
          responseType: 'code',
        });
      }
    } catch (error) {
      throw 'Incorrect email or password.';
    }
  }

  async signOut(): Promise<void> {
    // Terminates the session with Okta and removes current tokens.
    await this.oktaAuth.signOut();
  }
}
