import { Pipe, type PipeTransform } from '@angular/core';

@Pipe({
  name: 'statusPetColor',
})
export class StatusPetColorPipe implements PipeTransform {
  transform(status: string): string {
    switch (status.toLowerCase()) {
      case 'sad':
        return '#2196F3';

      case 'happy':
        return '#FFC107';

      case 'angry':
        return '#F44336';

      default:
        return 'black';
    }
  }

}
