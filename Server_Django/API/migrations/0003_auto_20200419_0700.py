# Generated by Django 3.0.1 on 2020-04-19 07:00

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('API', '0002_auto_20200229_1203'),
    ]

    operations = [
        migrations.RenameField(
            model_name='vehicle',
            old_name='pajak_dimulai',
            new_name='pajak_selanjutnya',
        ),
        migrations.RenameField(
            model_name='vehicle',
            old_name='servis_dimulai',
            new_name='servis_selanjutnya',
        ),
        migrations.RemoveField(
            model_name='vehicle',
            name='pajak_setiap_berapa_hari',
        ),
        migrations.RemoveField(
            model_name='vehicle',
            name='servis_setiap_berapa_hari',
        ),
    ]
