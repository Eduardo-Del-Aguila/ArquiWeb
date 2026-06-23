import { Component, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';

declare const JitsiMeetExternalAPI: any;

@Component({
  selector: 'app-jitsi-float',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './jitsi-float.html',
  styles: [`
    .float-btn {
      position: fixed;
      bottom: 32px;
      right: 32px;
      z-index: 9999;
      background: var(--color-green-400);;
      color: white;
      border: none;
      border-radius: 50%;
      width: 60px;
      height: 60px;
      font-size: 26px;
      cursor: pointer;
      box-shadow: 0 4px 12px rgba(0,0,0,0.3);
    }
    .jitsi-container {
      position: fixed;
      bottom: 100px;
      right: 32px;
      width: 480px;
      height: 360px;
      z-index: 9998;
      border-radius: 12px;
      overflow: hidden;
      box-shadow: 0 8px 24px rgba(0,0,0,0.4);
    }
  `]
})
export class JitsiFloatComponent implements OnDestroy {
  isOpen = false;
  private api: any = null;

  toggle() {
    this.isOpen ? this.cerrar() : this.abrir();
  }

  abrir() {
    this.isOpen = true;
    setTimeout(() => {
      this.api = new JitsiMeetExternalAPI('meet.jit.si', {
        roomName: 'EvaPet-Consulta-General',
        parentNode: document.getElementById('jitsi-container'),
        width: '100%',
        height: '100%',
        configOverwrite: {
          startWithAudioMuted: true,
          startWithVideoMuted: false,
        },
        interfaceConfigOverwrite: {
          SHOW_JITSI_WATERMARK: false,
          TOOLBAR_BUTTONS: ['microphone', 'camera', 'hangup', 'chat'],
        }
      });
    }, 100);
  }

  cerrar() {
    if (this.api) {
      this.api.dispose();
      this.api = null;
    }
    this.isOpen = false;
  }

  ngOnDestroy() { this.cerrar(); }
}