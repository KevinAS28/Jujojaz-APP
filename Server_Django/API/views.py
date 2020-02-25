from django.shortcuts import render
from django.http import JsonResponse
from Server_Django.library import jujojaz_login
from .models import *
from django.http.response import JsonResponse
from django.core import serializers
from django.http import HttpResponse
from django.forms import ModelForm
import json
import base64
import Server_Django.settings as settings
import os
from Server_Django.settings import *
# Create your views here.


def test(request):
    data = json.loads(request.POST['data'])
    image = data['image']
    with open(os.path.join(settings.BASE_DIR, 'image.jpg'), 'wb') as writer:
        b64_string = image.replace(" ", "+")
        print(b64_string)
        b64_byte = base64.decodebytes(b64_string.encode())
        print(len(b64_byte))
        #b64_string += "=" * ((4 - len(b64_string) % 4) % 4) #ugh
        writer.write(b64_byte)
    #return HttpResponse('test')
    return JsonResponse({"success": '1'})

def write_base64_file(base64_string, full_path):
    with open(full_path, 'wb') as writer:
        #b64_string = image.replace(" ", "+")
        b64_string=b64_string.replace(' ', '+')
        #print(b64_string)
        b64_byte = base64.decodebytes(b64_string.encode())
        #print(len(b64_byte))
        writer.write(b64_byte)
    
    


def create_account(request):
    #print(request.POST)
    data = json.loads(request.POST['data'])
    username = data['username']
    password = data['password']
    try:
        user = User.objects.get(username=json.loads(request.POST['data'])['username'])
        #username already exist
        print("user {} already exist".format(username))
        return JsonResponse({'success': '0', 'msg': 'user already exist'})
    except:
        print("user {} not found! creating it... done".format(username))
        User(username=username, password=password).save()
    
    return JsonResponse({'success': '1'})
    

@jujojaz_login
def get_all_vehicle(request):
    print(type(request.POST['data']))
    user = User.objects.get(username=json.loads(request.POST['data'])['username'])
    
    kendaraan = list(Vehicle.objects.filter(user=user))
    kendaraan_json = json.loads(serializers.serialize('json', kendaraan))
    data = {"success": "true", "data": kendaraan_json}      
    print(f'get all ${user.username}\'s vehicles succeed')  
    return JsonResponse(data)

@jujojaz_login
def edit_vehicle(request):
    data = json.loads(request.POST['data'])
    id_kendaraan = data["id_kendaraan"]
    kendaraan = Vehicle.objects.get(id=id_kendaraan)
    
    merk_nama = data["merk"]
    merks = list(VehicleMerk.objects.filter(name=merk_nama))
    if (len(merks)):
        merk_object = merks[0]    
    else:
        merk_object = VehicleMerk(name=merk_nama)
        merk_object.save()

    type_nama = data["tipe"]
    types = list(Vehicletype.objects.filter(name=type_nama))
    if (len(types)):
        type_object = types[0]    
    else:
        type_object = VehicleType(name=type_nama)
        type_object.save()
    
    user = User.objects.get(username=data['username'])

    kendaraan.tipe = type_object
    kendaraan.merk = merk_object
    kendaraan.pajak_setiap_berapa_hari = int(data["pajak_setiap_berapa_hari"])
    kendaraan.pajak_dimulai = data["pajak_dimulai"]
    kendaraan.servis_setiap_berapa_hari = int(data["servis_setiap_berapa_hari"])
    kendaraan.servis_dimulai = data["servis_dimulai"]        
    kendaraan.save()
    print(f'edit ${user.username}\'s vehicle succeed')  
    return JsonResponse({'success': '1'})

@jujojaz_login
def add_vehicle(request):
    data = json.loads(request.POST['data'])
    user = User.objects.get(username=data['username'])
    data = json.loads(data['data'])
    merk_nama = data["merk"]
    merks = list(VehicleMerk.objects.filter(name=merk_nama))
    if (len(merks)):
        merk_object = merks[0]    
    else:
        merk_object = VehicleMerk(name=merk_nama)
        merk_object.save()

    type_nama = data["tipe"]
    types = list(VehicleType.objects.filter(name=type_nama))
    if (len(types)):
        type_object = types[0]    
    else:
        type_object = VehicleType(name=type_nama)
        type_object.save()

    tipe = type_object
    merk = merk_object
    pajak_setiap_berapa_hari = int(data["pajak_setiap_berapa_hari"])
    pajak_dimulai = data["pajak_dimulai"]
    servis_setiap_berapa_hari = int(data["servis_setiap_berapa_hari"])
    servis_dimulai = data["servis_dimulai"]       
    foto_foto_name = str(user.id)+'_'+str(Vehicle.objects.filter(user=user).order_by('id')[:1][0])
    Vehicle(user=user, tipe=tipe, merk=merk, foto_foto_name=foto_foto_name, pajak_setiap_berapa_hari=pajak_setiap_berapa_hari, pajak_dimulai=pajak_dimulai, servis_setiap_berapa_hari=servis_setiap_berapa_hari, servis_dimulai=servis_dimulai).save()
    
    foto_file = data["photo"]
    write_base64_file(foto_file, os.path.join(VEHICLE_PHOTOS_DIR, foto_foto_name))
    
    print(f'add ${user.username}\'s vehicles succeed')  
    return JsonResponse({'success': '1'})

@jujojaz_login
def delete_vehicle(request):
    data = json.loads(request.POST['data'])
    user = User.objects.get(username=data['username'])
    id_kendaraan = data["id_kendaraan"]
    kendaraan = Vehicle.objects.get(id=id_kendaraan)
    kendaraan.delete()
    print(f'remove ${user.username}\'s vehicles succeed')  
    return JsonResponse({'success': '1'})

