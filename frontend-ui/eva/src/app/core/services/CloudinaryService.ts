import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class CloudinaryService {
  private http = inject(HttpClient);

  private cloudName = 'djxhy219c';
  private uploadPreset = 'evaAngularUserImg';

  subirImagen(file: File) {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('upload_preset', this.uploadPreset);

    return this.http.post<{ secure_url: string }>(
      `https://api.cloudinary.com/v1_1/${this.cloudName}/image/upload`,
      formData
    );
  }
}
